import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PostList from "../list/PostList";
import Button from "../ui/Button";
import data from '../../data.json';
import Template from "../ui/template";
import BTemplate from "../ui/BackgroundBox";
import SDrop from "../ui/SelectDrop";

//chae33 branch check
const MainTitleText = styled.p`
    font-size: 50px;
    font-weight: 900;
    text-align: center;
    color: #FFFFFF;
`;

const Wrapper = styled.div`
    padding: 16px;
    width:  calc(100% - 32px);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
 

    
`;

const Container = styled.div`
    width: 100%;
    max-width: 720px;



    & > * {
        :not(:last-child) {
            margin-bottom: 16px;
        }
    }
`;
const Space = styled.div`
  width: 15px;
  height: auto;
  display: inline-block;
`;

function MainPage(props) {
    const navigate = useNavigate();
  
    return (
      <Template>
        <MainTitleText>알쏭달쏭?!</MainTitleText>
        <BTemplate>
          <Wrapper>
            <Container>
              <Button
                title="살까 말까"
                onClick={() => {
                  navigate("/post-write");
                }}
              />
              <Space></Space>
              <Button
                title="할까 말까"
                onClick={() => {
                  navigate("/post-write");
                }}
              />
              <Space></Space>
              <Button
                title="갈까 말까"
                onClick={() => {
                  navigate("/post-write");
                }}
              />
              <SDrop/>
              <PostList
                posts={data}
                onClickItem={(item) => {
                  navigate(`/post/${item.id}`);
                }}
              />
            </Container>
          </Wrapper>
        </BTemplate>
      </Template>
    );
}
export default MainPage;
