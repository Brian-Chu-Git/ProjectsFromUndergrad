import { useOutletContext } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function SavedNote () {

    const {
        text: [text, setText],
        noteArray: [noteArray, addNote],
        deleteNote: [activeNote, deleteNote],
        getActiveNote: [getActiveNote],
        formatDate: [formatDate]
      } = useOutletContext();

    const navigate = useNavigate();

    const createMarkup = () => {
        let printText = "";
        printText = getActiveNote().body;
        return {__html: printText};
    }

    let printDate = "";







    return (
        <> 
            <div className = "savednotearea">
                <div className = "saved-header">
                    <div className = "saved-title">
                        <h1>{getActiveNote().title}</h1>
                        <small className = "saved-date">{getActiveNote() != false && getActiveNote().date}</small>
                    </div>
                    <div className = "go-edit"><button onClick = {()=> {navigate(`/notes/${noteArray.findIndex(note => note.id === activeNote) + 1}/edit`)}}>Edit</button></div>
                    <div className = "delete-note"><button onClick = {() => deleteNote()}>Delete</button></div>
                </div>
                <div dangerouslySetInnerHTML={createMarkup()} />
            </div>
        </>
    )
    




}

export default SavedNote;