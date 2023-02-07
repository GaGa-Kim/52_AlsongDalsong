import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import PostList from "../list/PostList";
import Button from "../ui/Button";
import data from '../../data.json';
import Template from "../ui/template";
import BTemplate from "../ui/BackgroundBox";
import SDrop from "../ui/SelectDrop";
import Userimg from "../ui/Userimg";
import Drop from "../ui/Drop";

//chae33 branch check
const MainTitleText = styled.p`
    font-size: 50px;
    font-weight: 900;
    text-align: center;
    color: #FFFFFF;
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
  width: 10px;
  height: 0.2px;
  display: inline-block;
`;

function MainPage(props) {
    const navigate = useNavigate();
  
    return (
      <Template>
        <Drop></Drop>
        <MainTitleText/>
          <Userimg
             onClick={() => {
             navigate("/auth/select-page");
          }}/>
        <BTemplate>
          <Space></Space>
            <Container>
              <Button
                title="살까 말까"
                onClick={() => {
                  navigate("/main-page");
                }}
              />
              <Space></Space>
              <Button
                title="할까 말까"
                onClick={() => {
                  navigate("/main-page");
                }}
              />
              <Space></Space>
              <Button
                title="갈까 말까"
                onClick={() => {
                  navigate("/main-page");
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
        </BTemplate>
      </Template>
    );
}
export default MainPage;
