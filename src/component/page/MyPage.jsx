import React from "react";
import styled from "styled-components";
import MyPageTemplate from "../ui/MyPageTemplate";
import "./mypage.css";
import Drop from "../ui/Drop";
import Template from "../ui/template";
import Userimg from "../ui/Userimg";
import { useNavigate } from "react-router-dom";


const MainTitleText = styled.p`
  font-size: 40px;
  font-weight: 900;
  text-align: center;
  color: #ffffff;
  letter-spacing: 2px;
  position: absolute;
  width: 241px;
  height: 58px;
  left: 23px;
  top: 27px;
`;
const Space = styled.div`
  width: 20px;
  height: 10px;
  display: inline-block;
`;


function MyPage(props) {
  const navigate = useNavigate();

  return (
    <Template>
    <Drop/>
    <Userimg
        onClick={() => {
        navigate("/auth/select-page");
    }}/>
    <MyPageTemplate>
       
   
      <div className="box">
        <img class="profile" src="/img/profile.png" />
      </div>

      <p className="name">말하는 감자</p>
      <p className="introduce">자신을 소개해 보세요</p>
      <br></br>
      <br></br>

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
        <div className="wrapper2">
          <div className="card1">
          <br></br>
          <div>
            <strong className="catebox">살/말</strong>
          </div>
          <br/>
          <img style={{width : '90px', height : '90px'}}src="https://imgnn.seoul.co.kr/img/upload/2021/09/23/SSI_20210923100149_V.jpg" />
          <br/>
          <p>Nike 운동화</p>
    
          <br></br>
          <input type="button" value="결정하기" className="dbtn" />
        </div>
        <Space />
        <div className="card1">
          <br></br>
          <div>
            <strong className="catebox">갈/말</strong>
          </div>
          <br/>
          <img style={{width : '90px', height : '90px'}}src="https://tongglobalcdn.visitkorea.or.kr/cms/resource/64/2932464_image2_1.bmp" />
          <p>서울시립미술관</p>
          <br></br>
          <input type="button" value="결정하기" className="dbtn" />
        </div>
        <Space />
        <div className="card1">
          <br></br>
          <div>
            <strong className="catebox">살/말</strong>
          </div>
          <br/>
          <img style={{width : '90px', height : '90px'}} src="https://cdn.mos.cms.futurecdn.net/uWjEogFLUTBc8mSvagdiuP.jpg" />
          <p>Apple 맥북</p>
          <br></br>
          <input type="button" value="결정하기" className="dbtn" />
        </div>
        </div>
        
      </div>
      <br />
      <br />
      <div class="wrapper">
        <p className="title">확정한 결정</p>
        <br></br>
        <br></br>
        <div className="wrapper2">
          <div class="card2">
          <br></br>
          <div>
            <strong className="catebox">할/말</strong>
          </div>
          <br/>
          <img style={{width : '90px', height : '90px'}} src="https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_2916,h_1944,f_auto/w_80,x_15,y_15,g_south_west,l_Klook_water_br_trans_yhcmh3/activities/eltdn5sjc3mnkq2sg3z7/NZONE%ED%80%B8%EC%8A%A4%ED%83%80%EC%9A%B4%EC%8A%A4%EC%B9%B4%EC%9D%B4%EB%8B%A4%EC%9D%B4%EB%B9%99-%ED%81%B4%EB%A3%A9KLOOK%ED%95%9C%EA%B5%AD.jpg" />
          <br/><br/>
          <p>스카이다이빙</p>
         
        </div>
        
        <Space />
        <div class="card2">
          <br></br>
          <div>
            <strong className="catebox">살/말</strong>
          </div><br/>
          <img style={{width : '90px', height : '90px'}} src="https://img.danawa.com/prod_img/500000/586/412/img/12412586_1.jpg?shrink=330:330&_v=20220623132255" />
          <br/><br/><p>애플워치</p>
          
        </div>
        <Space />
        <div class="card2">
          <br></br>
          <div>
            <strong className="catebox">갈/말</strong>
          </div><br/>
          <img style={{width : '90px', height : '90px'}} src="https://ticketimage.interpark.com/Play/image/large/21/21001791_p.gif" />
          <br/><br/>
          <p>블루룸 전시</p>
        </div>
        </div>
        
      </div>

      <br></br>
      <br></br>

      <p className="title">나의 구매 성향</p>
      <br></br>
      <div className="wrapper2">
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
      </div>
      <div className="wrapper2">
        <div className="graph-content">
        <br></br>
        당신은 '살까 말까' 에서 가장 많은 의견을 남겼고,<br></br>
        '할까 말까' 에서 가장 많은 결정을 했습니다.<br></br>
        <br></br>
      </div>
      </div>
     
    </MyPageTemplate>
    </Template>
  );
}

export default MyPage;