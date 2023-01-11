import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import SButton from "../ui/SelectButton";
import Template from "../ui/template";
import Stemplate from "../ui/Selectbox"
import B1img from "../ui/B1img";
import B2img from "../ui/B2img";
import Userimg from "../ui/Userimg";




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
          <MainTitleText>알쏭달쏭?!</MainTitleText>
          <Userimg
             onClick={() => {
             navigate("/");
          }}/>
                <Stemplate>
                  <SText>시간이 없다면?</SText>
                  <B1img/>
                <SButton
                    title="룰렛 PICK"
                    onClick={() => {
                        navigate("/main-page");
                    }}
                />
                </Stemplate>
              <Stemplate>
              <SText>의견이 필요하다면?</SText>
                <B2img/>
                 <SButton
                    title="투표 받기"
                    onClick={() => {
                        navigate("/main-page");
                    }}
                />
                </Stemplate>
        </Template>
      
       
    );
}

export default Selectpage;
