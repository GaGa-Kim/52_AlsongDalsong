import React from "react";
import styled from "styled-components";

const StyledIButton = styled.button`
    
    margin-top: 0px;
    font-size: 20px;
    font-weight: 900;
    cursor: pointer;
    width: 150px;
    height: 50px;
    margin-left: 13%;
    color:#F64177;
    background-color: white;
    border-shadow: none;
    border-width: 1px;
    border-color: white white #F64177 white;
    border-inline: 0px;
    text-align:left;
    margin-left:0px;
    `;
    

function Button(props) {
    const { title, onClick } = props;

    return <StyledIButton onClick={onClick}>{title || "button"}</StyledIButton>;
}

export default Button;
