import React from "react";
import styled from "styled-components";
import {KAKAO_AUTH_URL} from '../ui/Oauth';

const Ibutton = styled.button`
   text-align: center;
    font-size: 30px;
    font-weight: 900;
    font-family: "GmarketSansTTFMedium";
    border-width: 4px;
    border-radius: 50px;
    border-color:#FFCAC5 #790F05 #790F05 #FFCAC5;
    cursor: pointer;
    width: 250px;
    height: 100px;
    margin-top: 10%;

   
    color: #940F00;
    background-color: white;
    `;
    

function Button({children}) {
  return <Ibutton>{children}</Ibutton>;
}

export default function App() {
  return (
    <div>
      <a href={KAKAO_AUTH_URL} target="_blank" rel="noreferrer">
        <Button>카카오 로그인</Button>
      </a>
    </div>
  );
}
