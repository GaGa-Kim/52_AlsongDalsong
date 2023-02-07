import React from "react";
import styled from "styled-components";
import PostListItem1 from "./PostListItem1";
import PostListItem2 from "./PostListItem2";
import PostListItem3 from "./PostListItem3";
import PostListItem4 from "./PostListItem4";

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    font-family: 'GmarketSansTTFMedium';
    align-items: flex-start;
    justify-content: center;

    & > * {
        :not(:last-child) {
            margin-bottom: 20px;
        }
    }
`;

function PostList(props) {
    const { posts, onClickItem } = props;

    return (
        <Wrapper>
          <PostListItem1 />
          <PostListItem2 />
          <PostListItem3 />
          <PostListItem4 />
        </Wrapper>
    );
}

export default PostList;
