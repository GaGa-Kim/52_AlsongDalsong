import React from "react";
import styled from "styled-components";
import HorizonLine from "../ui/HorizontalLine";

const Wrapper = styled.div`
    width: calc(100% - 32px);
    padding: 0px 16px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: center;
    border: transper;
    border-radius: 8px;
    cursor: pointer;
    background: #F9F9F9;
    :hover {
        background: lightgrey;
    }
`;
const Wrapper_2 = styled.div`
    display: flex;
    flex-direction: row;

    .usericon{
        padding-top: 10px;
        padding-botton: 10px;
        font-size: 30px;
        color: #D9D9D9;
    }
   
    
`

const ContentText = styled.p`
    font-size: 13px;
    white-space: pre-wrap;
    text-align: left;
    font-style: normal;
    font-weight: 600;
    line-height: 1.5;
`;

function CommentListItem(props) {
    const { comment } = props;

    return (
        <Wrapper>
            <HorizonLine/>
            <Wrapper_2>
                <i className="usericon fa-solid fa-circle-user"></i>
                <ContentText>{comment.content}</ContentText>
            </Wrapper_2>
            
        </Wrapper>
    );
}

export default CommentListItem;
