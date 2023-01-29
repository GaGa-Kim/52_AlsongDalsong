import React from "react";
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";
import styled from "styled-components";
// Pages
import MainPage from './component/page/MainPage';
import PostWritePage from './component/page/PostWritePage';
import PostViewPage from './component/page/PostViewPage';
import Intropage from "./component/page/Intropage";
import Selectpage from "./component/page/Selectpage";
import Roulettepage from "./component/page/Roulettepage";
import Login from "./component/page/Login"
import WritePage from "./component/page/WritePage";

function App(props) {
    return (
        <BrowserRouter>
            <Routes>
                <Route index element={<Intropage />} />
                <Route path="auth/select-page" element={<Selectpage />} />
                <Route path="main-page" element={<MainPage />} />
                <Route path="post-write" element={<PostWritePage />} />
                <Route path="post/:postId" element={<PostViewPage />} />
                <Route path="wheel-page" element={<Roulettepage/>} />
                <Route path="login" element={<Login/>} />
                <Route path="writepage" element={<WritePage />} />
            </Routes>
        </BrowserRouter>
    );
}


export default App;
