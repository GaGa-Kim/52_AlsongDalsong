import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import BButton from "../ui/loginrb";
import Template from "../ui/template";
import Stemplate from "../ui/Selectbox"
import Button from "../ui/loginb"
import Userimg from "../ui/Userimg";
import Drop from "../ui/Drop"
import Kb from "../ui/kakaoimg"





const SText = styled.p`
    font-size: 25px;
    font-weight: 900;
    text-align: center;
    color: #940F00;
    margin-top: 6%;
    margin-bottom: 3%;
    letter-spacing: 2px;
    line-height:170%;
    
`;


const MainTitleText = styled.p`

width: 241px;
height: 58px;
margin-top: 25px;
margin-left: 18px;
font-family: 'Gmarket Sans TTF';
font-style: normal;
font-weight: 900;
font-size: 50px;
line-height: 57px
text-align: center;

color: #FFFFFF;

letter-spacing: 2px;
`;


function Selectpage(props) {
    const navigate = useNavigate();

    return (
      
         
        <Template>
    <Drop></Drop>
          <Userimg
             onClick={() => {
             navigate("/auth/select-page");
          }}/>
          <Kb/>
          <Button></Button>
          <BButton
                    title="룰렛 바로가기"
                    onClick={() => {
                        navigate("/wheel-page");
                    }}
                />
        </Template>
      
     
    );
}

export default Selectpage;
