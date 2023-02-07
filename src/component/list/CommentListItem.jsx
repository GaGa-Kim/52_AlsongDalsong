import React from "react";
import styled from "styled-components";
import HorizonLine from "../ui/HorizontalLine";
import HButton from "../ui/HeartButton";

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
  background: #f9f9f9;
  :hover {
    background: lightgrey;
  }
`;
const Wrapper_2 = styled.div`
  display: flex;
  flex-direction: row;
  .usericon {
    padding-top: 10px;
    padding-botton: 10px;
    font-size: 30px;
    color: #d9d9d9;
  }
  margin-left: 15px
  justify-content: space-beteween;

`;
const Wrapper_3=styled.div`
  margin-top: -7px;
  margin-left: auto;

`

const ContentText = styled.p`
  font-size: 11px;
  white-space: pre-wrap;
  text-align: left;
  font-style: normal;
  font-family: 'GMarketSansTTFMedium';
  font-weight: 500;
  line-height: 1.5;
  margin-top: 10px;
  color:#000000;
  
`;

function CommentListItem(props) {
  const { comment } = props;
  
  return (
    <Wrapper>
      <HorizonLine />
      <Wrapper_2>
        <i className="usericon fa-solid fa-circle-user"></i>
        <ContentText>{comment.content}</ContentText>
      </Wrapper_2>
      <Wrapper_3>
        <HButton />
      </Wrapper_3>
    </Wrapper>
  );
}

export default CommentListItem;
