import React from "react";
import styled from "styled-components";

const StyledIButton = styled.button`

font-size: 30px;
font-weight: 900;
font-family: "GmarketSansTTFMedium";
border-width: 4px;
border-radius: 50px;
border-color:#FFCAC5 #790F05 #790F05 #FFCAC5;
cursor: pointer;
width: 250px;
height: 100px;
margin: 0 auto;
margin-top: 7%;
margin-bottom: 7%;
color: #940F00;
background-color: white;
text-align: center;
    `;
    

function Button(props) {
    const { title, onClick } = props;

    return <StyledIButton onClick={onClick}>{title || "button"}</StyledIButton>;
}

export default Button;
