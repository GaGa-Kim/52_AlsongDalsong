import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import TextInput from "../ui/TextInput";
import Button from "../ui/Button";
import Template from "../ui/template";
import Userimg from "../ui/Userimg";
import BTemplate from "../ui/BackgroundBox";
import Drop from "../ui/Drop";

const MainTitleText = styled.p`
    font-size: 50px;
    font-weight: 900;
    text-align: center;
    color: #FFFFFF;
`;

const Wrapper = styled.div`
    padding: 16px;
    width: calc(100% - 32px);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
 
`;

const Container = styled.div`
    width: 100%;
    max-width: 720px;

    & > * {
        :not(:last-child) {
            margin-bottom: 16px;
        }
    }
`;

function PostWritePage(props) {
    const navigate = useNavigate();

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/api/post/save")
        .then(res => {
            return res.json();
        })
        .then(data =>{
            setTitle(data);
        });
    }, []);

    return (
      <Template>
        <Drop></Drop>
        <MainTitleText/>
          <Userimg
             onClick={() => {
             navigate("/auth/select-page");
          }}/>
        <BTemplate>
            <Container>
                <br></br>
                <TextInput
                    height={20}
                    value={title}
                    onChange={(event) => {
                        setTitle(event.target.value);
                    }}
                />

                <TextInput
                    height={480}
                    value={content}
                    onChange={(event) => {
                        setContent(event.target.value);
                    }}
                />

                <Button
                    title="글 작성하기"
                    onClick={() => {
                        navigate("/");
                    }}
                />
            </Container>
      </BTemplate>
    </Template>
    );
}

export default PostWritePage;
