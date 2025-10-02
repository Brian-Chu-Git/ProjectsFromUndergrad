import { useEffect, useRef, useState } from "react";
import { Outlet, useNavigate, Link } from "react-router-dom";
import NoteList from "./NoteList";
import { v4 as uuidv4 } from "uuid";
import { currentDate } from "./utils";
import { GoogleOAuthProvider } from '@react-oauth/google';
import axios from 'axios';
import { googleLogout, useGoogleLogin } from '@react-oauth/google';

const localStorageKey = "lotion-v1";

function Layout() {
  const navigate = useNavigate();
  const mainContainerRef = useRef(null);
  const [collapse, setCollapse] = useState(false);
  const [notes, setNotes] = useState([]);
  const [editMode, setEditMode] = useState(false);
  const [currentNote, setCurrentNote] = useState(-1);
  const [ user, setUser ] = useState(JSON.parse(localStorage.getItem("curUser")));
  const [ profile, setProfile ] = useState(JSON.parse(localStorage.getItem("curProfile")));
  const [ SignIn, setSignIn] = useState(false);

  // useEffect(() => {
  //    const height = mainContainerRef.current.offsetHeight;
  //    mainContainerRef.current.style.maxHeight = `${height}px`;
  //    const existing = localStorage.getItem(localStorageKey);
  //    if (existing) {
  //      try {
  //        setNotes(JSON.parse(existing));
  //      } catch {
  //        setNotes([]);
  //      }
  //    }
  //  }, []);

  // useEffect(() => {
  //    localStorage.setItem(localStorageKey, JSON.stringify(notes));
  //  }, [notes]);

  useEffect(() => {
    if (currentNote < 0) {
      return;
    }
    if (!editMode) {
      navigate(`/notes/${currentNote + 1}`);
      return;
    }
    navigate(`/notes/${currentNote + 1}/edit`);
  }, [notes]);

  const saveNote = async (note, index) => {
    console.log("clicked")
    console.log(note)

    note.body = note.body.replaceAll("<p><br></p>", "");
    console.log(note.body)
    setNotes([
      ...notes.slice(0, index),
      { ...note },
      ...notes.slice(index + 1),
    ]);
    setCurrentNote(index);
    const res = await fetch("https://fkquqjeodcpybr2zxrq5aiy27a0zfnre.lambda-url.ca-central-1.on.aws/",  {
      method: "POST",
      headers: {
        "Context-Type": "application/json"
      },
      body: JSON.stringify({...note, email: profile.email}),
      });
    setEditMode(false);
  };

  const deleteNote = async (index) => {
    console.log("clicked")
    console.log(notes[index])
    const res = await fetch("https://fd5yba7ydz6txtxchpdy5zbprq0ggvjk.lambda-url.ca-central-1.on.aws/",  {
      method: "DELETE",
      headers: {
        "Context-Type": "application/json"
      },
      body: JSON.stringify({email: profile.email, id: notes[index].id}),
      }
    );
    setNotes([...notes.slice(0, index), ...notes.slice(index + 1)]);
    setCurrentNote(0);
    setEditMode(false);
  };

  useEffect(() => {
    const getNoteEffect = async () =>{
      if (profile.email){
        console.log(profile.email)
        const res = await fetch(`https://3bqjdylqw6543aezvr5f25cdsu0lixdr.lambda-url.ca-central-1.on.aws?email=${profile.email}`)
        const noteArr = await res.json();
        setNotes(noteArr);
      }
    };
    getNoteEffect()
  }, [profile]);

  const addNote = () => {
    setNotes([
      {
        id: uuidv4(),
        title: "Untitled",
        body: "",
        when: currentDate(),
      },
      ...notes,
    ]);
    setEditMode(true);
    setCurrentNote(0);
  };

  const login = useGoogleLogin({
      onSuccess: (codeResponse) => setUser(codeResponse),
      onError: (error) => console.log('Login Failed:', error)
  });

  useEffect(
      () => {
          if (user) {
              axios
                  .get(`https://www.googleapis.com/oauth2/v1/userinfo?access_token=${user.access_token}`, {
                      headers: {
                          Authorization: `Bearer ${user.access_token}`,
                          Accept: 'application/json'
                      }
                  })
                  .then((res) => {
                      setProfile(res.data);
                      console.log(res.data);
                  })
                  .catch((err) => console.log(err));
          }
      },
      [ user ]
  );

  // log out function to log the user out of google and set the profile array to null
  const logOut = () => {
      googleLogout();
      setProfile(null);
      localStorage.setItem("curUser", null);
      localStorage.setItem("curProfile", null);
  };

  useEffect (
    () => {
      localStorage.setItem("curUser", JSON.stringify(user));
      localStorage.setItem("curProfile", JSON.stringify(profile));
    }, [ user ]
  );




  return (
    <div id="container">
      <header>
        <aside>
          <button id="menu-button" onClick={() => setCollapse(!collapse)}>
            &#9776;
          </button>
        </aside>
        <div id="app-header">
          <h1>
            <Link to="/notes">Lotion</Link>
          </h1>
          <h6 id="app-moto">Like Notion, but worse.</h6>
        </div>
        <aside>&nbsp;</aside>
        {(profile) && (<div id = "logout-div">{profile.name} 
          <button id = "logout-button" onClick = {() => {logOut(); setSignIn(false)}}>(Log out)</button>
        </div>)}
      </header>
      <div id="main-container" ref={mainContainerRef}>
        {profile ? (
        <div id="dived-container">        
        <aside id="sidebar" className={collapse ? "hidden" : null}>
          <header>
            <div id="notes-list-heading">
              <h2>Notes</h2>
              <button id="new-note-button" onClick={addNote}>
                +
              </button>
            </div>
          </header>
          <div id="notes-holder">
            <NoteList notes={notes} />
          </div>
        </aside>
        <div id="write-box">
          <Outlet context={[notes, saveNote, deleteNote]} />
        </div>
        </div>) : 
        (
            <div id="center-the-button">
                <button onClick={() => {login(); setSignIn(true)}} id="signin-button">Sign into Lotion with<img src = "https://companieslogo.com/img/orig/GOOG-0ed88f7c.png?t=1633218227" height = "13px" width = "13px" id = "googleImage"/> </button>
            </div>
        )}
      </div>
    </div>
  );
}

export default Layout;
