import React from "react";
import styled from "styled-components";
import MyPageTemplate from "../ui/MyPageTemplate";
import './mypage.css';
import Card from "../ui/Card";
import CardSC from "../ui/CardSC";

const MainTitleText = styled.p`
    font-size: 40px;
    font-weight: 900;
    text-align: center;
    color: #FFFFFF;
    letter-spacing: 2px;
    position: absolute;
    width: 241px;
    height: 58px;
    left: 23px;
    top: 27px;
`;





function MyPage(props) {

    return (
        <MyPageTemplate>
            <MainTitleText>알쏭달쏭?!</MainTitleText>
            
                
                
            <div className="box">
                <img class="profile" src="/img/profile.png" />
            </div>

            <p className="name">말하는 감자</p>
            <p className="introduce">자신을 소개해 보세요</p>
            <br></br><br></br>
            <br></br><br></br>
            
            <span className="flex-container">
            <p className="title">적립</p>
            <div class="progress-bar">           
                <div class="progress">37%</div>
            </div>
            </span>
            <br></br>

            <p className="title">확정한 결정</p><br></br>
            <span className="flex-container">
            
            <div className="mg"><Card title="하우스 도산" select="갈/말" image="img/hausdosan.png" /></div>
            <div className="mg"><Card title="필름 카메라" select="살/말" image="img/camera.png" /></div>
            <div className="mg"><Card title="필라테스" select="할/말" image="img/pilates.png" /></div>
            </span>
            

            

            <p className="title">스크랩</p>
            <span className="flex-container">
            
            <div className="mg"><CardSC title="하우스 도산" select="갈/말" image="img/hausdosan.png" /></div>
            <div className="mg"><CardSC title="필름 카메라" select="살/말" image="img/camera.png" /></div>
            <div className="mg"><CardSC title="필라테스" select="할/말" image="img/pilates.png" /></div>
            </span>

            <p className="title">나의 구매 성향</p>

        </MyPageTemplate>
           
       
    );
}

export default MyPage;
