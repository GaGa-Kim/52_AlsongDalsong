import React from "react";
import styled from "styled-components";
import HorizonLine from "../ui/HorizontalLine";
import Likes from "../ui/likes";


const Wrapper = styled.div`
    width: calc(100% - 32px);
    
    padding-top: 10px;
    padding-right: 16px;
    padding-bottom: 10px;
    padding-left: 16px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: center;
    border-color:transparent;
    border-radius: 20px;
    cursor: pointer;
    background: #EFEFEF;
    :hover {
        background: lightgrey;
    }
    box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.25);
    
    .DecisionIcon {
        font-size: 30px;
        position: absolute;
        margin-left: 75%;
        margin-top: -40%;
        color: #FA0050;
    }

`;
const NameandDay = styled.p`
    position: absolute;
    width: 280px;
    height: 40px;

    font-family: 'Inter';
    font-style: normal;
    font-weight: 500;
    font-size: 13px;
    line-height: 16px;
    

`
const TitleText = styled.p`

    width: 280px;
    height: 5px;

    font-family: 'Inter';
    font-style: normal;

    font-weight: 600;
    font-size: 17px;

    text-align: left;
    

`;

const ContentText = styled.p`
   
    white-space: pre-wrap;
    font-family: 'Inter';
    font-style: normal;
    font-weight: 500;
    font-size: 12px;
    line-height: 15px;
    display: flex;
    text-align: left;
    padding-bottom: 20px;
`;
const Space = styled.div`
  width: 15px;
  height: auto;
  display: inline-block;
`;


function PostListItem(props) {
    const { post, onClick } = props;

    return (
        <Wrapper onClick={onClick}>
            <NameandDay></NameandDay>
            <TitleText>{post.title}</TitleText>
            <i className="DecisionIcon fa-regular fa-circle-check"></i>
            <ContentText>{post.content}</ContentText>
            <HorizonLine/>
            <Likes/>
           
        </Wrapper>
    );
}

export default PostListItem;
