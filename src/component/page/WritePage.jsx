import React from "react";
import styled from "styled-components";
import MNButton from "../ui/menuBT";
import PageTemplate from "../ui/PageTemplate";
import './practice.css';

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





function Writepage(props) {

    return (
        <PageTemplate>
            <MainTitleText>알쏭달쏭?!</MainTitleText>
            <br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br>
                
                <span className="flex-container">
                <div><MNButton title="카테고리" /></div>
                <form>
                패션 <input type="radio" name="category" value="fashion" checked />
                가전 <input type="radio" name="category" value="appliance" />
                뷰티 <input type="radio" name="category" value="beauty" />
                식품 <input type="radio" name="category" value="grocery" />
                </form>

                </span>

                <span className="flex-container">
                <div><MNButton title="누가" /></div>
                <form>
            
                남성 <input type="radio" name="gender" value="male" checked />
                여성 <input type="radio" name="gender" value="female" />

            
                </form>
                </span>
                <span className="flex-container">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <form>
            
                10대 <input type="radio" name="age" value="10" checked />
                20대 <input type="radio" name="age" value="20" />
                30대 <input type="radio" name="age" value="30" />
                40대 <input type="radio" name="age" value="40" />

        
                </form>
                </span>

                <span className="flex-container">
                <div><MNButton title="언제" /></div><div><input type='date' id='currentDate' class="choice"/></div></span>

                <span className="flex-container">
                <div><MNButton title="무엇을" /></div><div><textarea cols="15" rows="2" placeholder="직접입력"></textarea></div></span>

                <span className="flex-container">
                <div><MNButton title="내용" /></div><div><textarea cols="30" rows="8" class="rad"></textarea></div></span>

                <span className="flex-container">
                <div><MNButton title="링크" /></div><div><textarea cols="15" rows="2" placeholder="직접입력"></textarea></div></span>

                <span className="flex-container">
                <div><MNButton title="중요도" /></div>
                <form name="myform" id="myform" method="post" action="./save">
      <fieldset>
          <label for="rate1">⭐</label><input type="radio" name="rating" value="5" id="rate1" />
          <label for="rate2">⭐</label><input type="radio" name="rating" value="4" id="rate2" />
          <label for="rate3">⭐</label><input type="radio" name="rating" value="3" id="rate3" />
          <label for="rate4">⭐</label><input type="radio" name="rating" value="2" id="rate4" />
          <label for="rate5">⭐</label><input type="radio" name="rating" value="1" id="rate5" />
      </fieldset>
                </form>
                </span>

                <span className="flex-container">
                <div><MNButton title="사진" /></div>
                <form>
                        <input type = "file" name = "fileupload" accept = "image/*" class="filechoose"/>
                </form>
                </span>
                


    
        </PageTemplate>
           
       
    );
}

export default Writepage;
