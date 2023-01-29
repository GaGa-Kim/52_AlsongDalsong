import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styled from "styled-components";
import CommentList from "../list/CommentList";
import TextInput from "../ui/TextInput";
import Button from "../ui/Button";
import Bbutton from "../ui/BackButton";
import data from "../../data.json";
import Template from "../ui/template";
import BTemplate from "../ui/BackgroundBox";
import PTemplate from "../ui/PostBox";
import Likes from "../ui/likes";
import UserImage from "../ui/woman.png";

const MainTitleText = styled.p`
    font-size: 50px;
    font-weight: 900;
    text-align: center;
    color: #FFFFFF;
`;

const Wrapper = styled.div`
    padding: 16px;
    width: calc(100% - 32px);
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
const TopContainer = styled.div`
  padding: 0px;
  border: transper;
  border-radius: 8px;
  background-color: #F9F9F9;
  #userimage {
    width: 42px;
    height: 40px;
    position: absolute;
    left: 20%;
    right: 0%;
    top: 10%;
    bottom: 0%;
  }
`;

const PostContainer = styled.div`
    padding: 8px 16px;
    border: transper;
    border-radius: 8px;
    background: #F9F9F9;
`;
const LikesContainer = styled.div`
  padding: 0px;
  border: transper;
  border-radius: 8px;
  background: #F9F9F9;
  margin: 0 auto;
  display: block;
`;
const TitleText = styled.p`
  font-size: 17px;
  font-weight: 600;
  line-height: 23px;
  color: #000000;
  text-align: left;
`;

const ContentText = styled.p`
    font-style: normal;
    font-size: 13px;
    font-weight: 500;
    text-align: left;
    line-height: 1.5;
    white-space: pre-wrap;
    color: #000000;
`;

const CommentLabel = styled.p`
    padding-top: 15px;
    padding-left: 15px;
    font-weight: 600;
    font-size: 15px;
    line-height: 18px;
    color: #000000;
`;
const Space = styled.div`
  width: 15px;
  height: auto;
  display: inline-block;
`;

function PostViewPage(props) {
    const navigate = useNavigate();
    const { postId } = useParams();

    const post = data.find((item) => {
        return item.id == postId;
    });

    const [comment, setComment] = useState("");
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
            <PTemplate>
              <TopContainer>
                <Bbutton
                  icon="fa-solid fa-arrow-left"
                  onClick={() => {
                    navigate("/");
                  }}
                />
                <img id="userimage" src={UserImage} />
              </TopContainer>
              <PostContainer>
                <TitleText>{post.title}</TitleText>
                <ContentText>{post.content}</ContentText>
              </PostContainer>
              <LikesContainer>
                <Likes />
              </LikesContainer>
              
              <CommentLabel>댓글</CommentLabel>

              <CommentList comments={post.comments} />

              <TextInput
                height={20}
                value={comment}
                onChange={(event) => {
                  setComment(event.target.value);
                }}
              />

              <Button
                title="댓글 작성하기"
                onClick={() => {
                  navigate("/");
                }}
              />
            </PTemplate>
          </Container>
        </Wrapper>
      </BTemplate>
    </Template>
  );
}
        

export default PostViewPage;
