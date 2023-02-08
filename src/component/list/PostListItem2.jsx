import styled from "styled-components";
import HorizonLine from "../ui/HorizontalLine";
import Likes from "../ui/likes";
import React, { useEffect, useState } from 'react';
import axios from 'axios';

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
  border-color: transparent;
  border-radius: 20px;
  cursor: pointer;
  background: #efefef;
  :hover {
    background: lightgrey;
  }
  box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.25);

  .DecisionIcon {
    font-size: 30px;
    position: absolute;
    margin-left: 60%;
    margin-top: -18%;
    color: #fa0050;
  }
`;
const NameandDay = styled.p`
  position: absolute;
  width: 280px;
  height: 40px;
  font-family: "Inter";
  font-style: normal;
  font-weight: 500;
  font-size: 13px;
  line-height: 16px;
`;

const TitleText = styled.p`
  width: 280px;
  height: 5px;
  font-family: "Inter";
  font-style: normal;
  font-weight: 600;
  font-size: 17px;
  font-family: 'GmarketSansTTFMedium';
  text-align: left;
  padding-top: 10px;
  color: #000000;
  
`;
const ContentText = styled.p`
  white-space: pre-wrap;
  font-family: "Inter";
  font-style: normal;
  font-weight: 500;
  font-size: 12px;
  font-family: 'GmarketSansTTFMedium';
  line-height: 15px;
  display: flex;
  text-align: left;
  padding-bottom: 20px;
  padding-top: 30px;
  color: #000000;
`;
const Space = styled.div`
  width: 15px;
  height: auto;
  display: inline-block;
`;

const TestPage = () => {

  const [data , setData] = useState([]);
  
  useEffect(() => {
      axios.get().then(Response => {
      setData(Response.data);
      console.log(Response.data);
  }).catch((Error)=> {
      console.log(Error) ;
  })
      }, [])

  return (
      <>
      {data.length}
    {data && 
      <Wrapper>
      <NameandDay></NameandDay>
      <TitleText>{data.what} 살까 말까</TitleText>
      <i className="DecisionIcon fa-regular fa-circle-check"></i>
      <ContentText>{data.content}</ContentText>
      <HorizonLine />
      <Likes />
    </Wrapper>
    
    }
      </>
  );
};

export default TestPage;