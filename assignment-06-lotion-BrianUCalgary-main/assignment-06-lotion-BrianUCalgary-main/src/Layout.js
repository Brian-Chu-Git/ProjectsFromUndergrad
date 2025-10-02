import {Outlet, useNavigate, useParams, useLocation} from "react-router-dom";
import React, {useState, useEffect} from "react";
import { v4 as uuidv4 } from 'uuid';

import Sidebar from "./Sidebar";



function Layout() {



    const [noteArray, setNotes] = useState(JSON.parse(localStorage.getItem("dataKey")) || []);
    const [activeNote, setActiveNote] = useState(false);
    const [text, setText] = useState("");

    const navigate = useNavigate();

    let {noteid} = useParams();

    let curUrl = useLocation();

    const addNote = () => {
        var now = new Date();
        now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
        now.setMilliseconds(null);
        now.setSeconds(null);
        var today = now.toISOString().slice(0, -1);

        console.log(today);


        const newNote = {
            id: uuidv4(),
            title: "Untitled Note",
            body: "",
            editorDate: today,
            date: formatDate(Date.now()),
        };

        setNotes([newNote, ...noteArray]);
        setActiveNote(newNote.id);
    }

    function toggleSidebar() {
        var hidesidebar = document.getElementById("hidden");
        if (hidesidebar.style.display === "none") {
          hidesidebar.style.display = "flex";
        } else {
          hidesidebar.style.display = "none";
        }
    }

    const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
        minute: "numeric",
    };
    
    const formatDate = (when) => {
        const formatted = new Date(when).toLocaleString("en-US", options);
        if (formatted === "Invalid Date") {
            return "";
        }
        return formatted;
    };

    const deleteNote = () => {
        const answer = window.confirm("Are you sure?");
        if (answer) {
            let activePos = noteArray.findIndex(note => note.id === activeNote)
            setNotes(noteArray.filter((note) => note.id !== activeNote))
            if (noteArray.length >= 2) {
                try {
                    setActiveNote(noteArray[activePos + 1].id);
                }
                catch(err){
                    setActiveNote(noteArray[noteArray.length - 2].id);
                }
            }
            else {
                navigate(`/notes`);
            }
        }
    }

    const getActiveNote = () => {
        if (noteArray.find(note => note.id === activeNote)) {
            return noteArray.find(note => note.id === activeNote);
        }
        else {
            return false;
        }
    }

    useEffect(() => {
        console.log(noteid);
        if (noteArray[noteid - 1]) {
            setActiveNote(noteArray[noteid - 1].id);
        }
    }, [])

    useEffect (() => {
        if (activeNote) {
            if (curUrl.pathname.includes("edit")) {
                navigate (`notes/${noteArray.findIndex(note => note.id === activeNote) + 1}/edit`)
            }
            else {
                navigate (`notes/${noteArray.findIndex(note => note.id === activeNote) + 1}/`)
            }
        }
        
    }, [activeNote])

    useEffect(() => {
        const notes = noteArray;
        localStorage.setItem('dataKey', JSON.stringify(notes));
        console.log(JSON.stringify(notes));
    }, [noteArray])



    return(
        <> 
            <div className = "headerBar">
                <button id = "toggleButton" className = "toggleButton" onClick = {() => toggleSidebar()}>&#9776;</button>
                <div className = "title">
                    <div className = "title-part"><h1>Lotion</h1></div>
                    <div className = "subtitle-part"><h3>Like notion, but worse.</h3></div>
                </div>
            </div>
            <div className = "mainparts">
                <div className = "hidden" id = "hidden">
                    <Sidebar 
                        noteArray = {noteArray} 
                        addNote = {addNote}
                        activeNote = {activeNote}
                        setActiveNote = {setActiveNote}
                    />
                </div>
                <div className = "maintext">
                    <Outlet context={{
                        text: [text, setText],
                        noteArray: [noteArray, addNote],
                        deleteNote: [activeNote, deleteNote],
                        getActiveNote: [getActiveNote],
                        formatDate: [formatDate]}}/>
                </div>
            </div>
        </>

    );

}

export default Layout;