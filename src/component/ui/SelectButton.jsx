import React from "react";
import styled from "styled-components";

const StyledIButton = styled.button`
   text-align: center;
    font-size: 30px;
    font-weight: 600;
    border-width: 4px;
    border-radius: 50px;
    border-color:#FFCAC5 #790F05 #790F05 #FFCAC5;
    cursor: pointer;
    width: 173.21px;
    height: 57.65px;
    margin-top:3%;
   
    color: #940F00;
    background-color: white;
    `;
    

function Button(props) {
    const { title, onClick } = props;

    return <StyledIButton onClick={onClick}>{title || "button"}</StyledIButton>;
}

export default Button;
