import React from "react";
import styled from "styled-components";
import MyPageTemplate from "../ui/MyPageTemplate";
import './mypage.css';


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

            <p className="title">적립</p>
            <span className="flex-container">
            <div class="progress-bar">           
                <div class="progress">37%</div>
            </div>
            </span>
            <br></br>

            
            

            

           

            

            <div className="wrapper">
            <p className="title">스크랩</p>
     <br></br>
      <div className="card1">
        <br></br>
        <div><strong className="catebox">갈/말</strong></div>
        <img src="img/01.jpg" />
        <p>title</p>
        <br></br>
        <input type='button' 
        value='결정하기' className="dbtn"/>
      </div>
      <div className="card1">
        <br></br>
        <div><strong className="catebox">갈/말</strong></div>
        <img src="img/01.jpg" />
        <p>title</p>
        <br></br>
        <input type='button' 
        value='결정하기' className="dbtn"/>
      </div>
      <div className="card1">
        <br></br>
        <div><strong className="catebox">갈/말</strong></div>
        <img src="img/01.jpg" />
        <p>title</p>
        <br></br>
        <input type='button' 
        value='결정하기' className="dbtn"/>
      </div>
    </div>


    <div class="wrapper">
    <p className="title">확정한 결정</p><br></br><br></br>
        <div class="card2">
            <br></br>
            <div><strong className="catebox">갈/말</strong></div>
            <img src="img/01.jpg" />
            <p>title</p>
        </div>
        <div class="card2">
            <br></br>
            <div><strong className="catebox">갈/말</strong></div>
            <img src="img/01.jpg" />
            <p>title</p>
        </div>
        <div class="card2">
            <br></br>
            <div><strong className="catebox">갈/말</strong></div>
            <img src="img/01.jpg" />
            <p>title</p>
        </div>


    </div>

   <br></br><br></br>

    
   <p className="title">나의 구매 성향</p><br></br>
   <div className="wrwr">
   <span class="flex-container">
    <section>
        <div className="graph stack1">
          <span>75%</span>    
        </div>
        <div className="graph stack2">
          <span>60%</span>    
        </div>
        <div className="graph stack3">
          <span>25%</span>    
        </div>
    </section>
    <section>
        <div className="graph stack1">
          <span>75%</span>    
        </div>
        <div className="graph stack2">
          <span>60%</span>    
        </div>
        <div className="graph stack3">
          <span>25%</span>    
        </div>
    </section>
    <section>
        <div className="graph stack1">
          <span>75%</span>    
        </div>
        <div className="graph stack2">
          <span>60%</span>    
        </div>
        <div className="graph stack3">
          <span>25%</span>    
        </div>
    </section>
    
    </span>
    </div>
    <div className="graph-content"><br></br>
        당신은 '살까 말까' 에서 가장 많은 의견을 남겼고,<br></br>
          '할까 말까' 에서 가장 많은 결정을 했습니다.<br></br><br></br></div>

        </MyPageTemplate>
           
       
    );
}

export default MyPage;
