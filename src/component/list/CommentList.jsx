import React from "react";
import styled from "styled-components";
import CommentListItem1 from "./CommentListItem1";
import CommentListItem4 from "./CommentListItem4";

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: center;

    & > * {
        :not(:last-child) {
            margin-bottom: 16px;
        }
    }
`;

function CommentList(props) {
    const { comments } = props;

    return (
        <Wrapper>
            <CommentListItem1 />
            <CommentListItem4 />
        </Wrapper>
    );
}

export default CommentList;
