import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import IButton from "../ui/IntroButton";
import Template from "../ui/template";
import Iimg from "../ui/Introimg";

const IntroText = styled.p`
    font-size: 20px;
    font-weight: 900;
    text-align: center;
    color: #FFFFFF;
    margin-top: 20%;
    margin-bottom: 1%;
    letter-spacing: 2px;
    line-height:170%;
    
`;


const MainTitleText = styled.p`
    font-size: 50px;
    font-weight: 900;
    text-align: center;
    color: #FFFFFF;
    letter-spacing: 2px;
`;



function Intropage(props) {
    const navigate = useNavigate();

    return (
        <Template>
           <IntroText>이거 살까 말까?<br></br>여기 갈까 말까?<br></br>이거 할까 말까?<br></br>누군가가 결정해주길 원한다면?</IntroText>
            <MainTitleText>알쏭달쏭?!</MainTitleText>
                <Iimg/>
                <IButton
                    title="START"
                    onClick={() => {
                        navigate("/select-page");
                    }}
                />
        </Template>
           
       
    );
}

export default Intropage;
