import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import SButton from "../ui/SelectButton";
import Template from "../ui/template";
import Stemplate from "../ui/Selectbox"
import B1img from "../ui/B1img";
import B2img from "../ui/B2img";
import Userimg from "../ui/Userimg";
import Drop from "../ui/Drop";
import Wheel1 from "../ui/Wheel1"
import "../ui/styles.css";
import DraggeableForm from "../ui/DraggeableForm";




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

margin bottom:30%


letter-spacing: 2px;
`;


function Roulettepage(props) {
    const navigate = useNavigate();

    return (
      
          
        <Template>
        <Drop/>
        <MainTitleText/>
          <Userimg
             onClick={() => {
             navigate("/new");
          }}/>
            
                
             <DraggeableForm />
                 
         
        </Template>
      
       
    );
}

export default Roulettepage;
