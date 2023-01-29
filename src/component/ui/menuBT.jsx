import React from "react";
import styled from "styled-components";

const StyledIButton = styled.button`
    margin-top: 0px;
    font-size: 13px;
    font-weight: 100;
    border-width: 4px;
    border-radius: 50px;
    border-color:#FFCAC5 #790F05 #790F05 #FFCAC5;
    cursor: pointer;
    top: 190px;
    margin-left: 3%;
    color:#F64177;
    width: 73px;
    height: 39.59px;
    `;
    

function Button(props) {
    const { title } = props;

    return <StyledIButton>{title || "button"}</StyledIButton>;
}

export default Button;
