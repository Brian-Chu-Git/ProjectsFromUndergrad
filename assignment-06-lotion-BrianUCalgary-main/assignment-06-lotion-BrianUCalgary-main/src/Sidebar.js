function Sidebar ({noteArray, addNote, activeNote, setActiveNote}) {
    


    return (

        <div className = "sidebar" id = "sidebar">
            <div className = "sidebar-header">
                <h1 className = "sidebar-title">Notes</h1>
                <h1 className = "new-note"><button onClick={addNote}>+</button></h1>
            </div>
            <div className = "sidebar-notes">
                {noteArray.map((note) => (
                    <button 
                        className = {`sidebar-note ${note.id === activeNote && "active"}`} 
                        onClick = {() => setActiveNote(note.id)}>
                        <strong className = "sidebar-note-title">{note.title}</strong>
                        <div className = "sidebar-date">{note.date}</div>
                        <div className = "sidebar-preview" dangerouslySetInnerHTML={{__html: note.body == "<p><br></p>" ? "..." 
                            : (`${note.body.substr(0, 50)} ${note.body.length > 50 || note.body.length == 0 ? "..." : ""}`)}}></div>
                    </button>
                ))} 
            </div>
        </div>
            
    )
}

export default Sidebar;