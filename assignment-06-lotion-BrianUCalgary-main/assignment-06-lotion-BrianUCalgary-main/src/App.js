import  {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom';

import Layout from "./Layout";
import Notes from "./Notes";
import SavedNote from "./SavedNote";
import EditNote from "./EditNote";


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element = {<Layout />}>
          <Route path="/" element = {<Navigate to = "/notes"/>}></Route>
          <Route path="/notes" element={<Notes/>} />
          <Route path="/notes/:noteid" element={<SavedNote/>} />
          <Route path="/notes/:noteid/edit" element={<EditNote/>} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App;
