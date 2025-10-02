import { useState, useEffect } from 'react';
import ReactQuill from 'react-quill';
import { useOutletContext } from "react-router-dom";
import 'react-quill/dist/quill.snow.css';
import { useNavigate } from "react-router-dom";

function EditNote () {
    
    const {
        text: [text, setText],
        noteArray: [noteArray, addNote],
        deleteNote: [activeNote, deleteNote],
        getActiveNote: [getActiveNote],
        formatDate: [formatDate]
      } = useOutletContext();
    const navigate = useNavigate();

    const [title, setTitle] = useState(getActiveNote().title); 
    const [date, setDate] = useState(getActiveNote().date);

    const saveAndMove = () => {
        getActiveNote().body = text;
        getActiveNote().title = title;
        getActiveNote().date = formatDate(date);
        getActiveNote().editorDate = date;
        const notes = noteArray;
        localStorage.setItem('dataKey', JSON.stringify(notes));
        console.log(JSON.stringify(notes));
        navigate(`/notes/${noteArray.findIndex(note => note.id === activeNote) + 1}`);
    }

    const dateChangeHandler = (event) => {
        date = setDate(event.target.value);
    }

    useEffect(() => {
        setText(getActiveNote().body);
    }, [activeNote])

    useEffect(() => {
        setTitle(getActiveNote().title);
        setDate(getActiveNote().editorDate);
    }, [activeNote])


    return (
        
        <div className = "editnotearea">
            <div className = "editor-header">
                <div className = "editor-title">
                    <div>
                        <input id = "editor-input-title" placeholder = "Untitled" value = {title} onChange = {event => setTitle(event.target.value)}></input>
                    </div>
                    <div>
                        <input id = "date-select" type = "datetime-local" value = {date} onChange = {dateChangeHandler} step = "1" className = "note-date"></input>
                    </div>
                </div>
                <div className = "go-save"><button onClick = {() => saveAndMove()}>Save</button></div>
                <div className = "delete-note"><button onClick = {() => deleteNote()}>Delete</button></div>
            </div>
            <ReactQuill className='editor' theme = "snow" placeholder="Your note Here" value = {text} onChange={setText}/>
        </div>
        
    )
    




}

export default EditNote;