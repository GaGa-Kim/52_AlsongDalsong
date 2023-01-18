import alsong from "../ui/alsong.png";
import './Drop.css';
import Button from "../ui/Dropbutton";
import { useNavigate } from "react-router-dom";
import React, {useState, useEffect, useRef} from 'react';

function Drop() {

  const [open, setOpen] = useState(false);
  const navigate = useNavigate();

  const navigateHome = () => {
    // 👇️ navigate to /
    navigate('/');
  };

  let menuRef = useRef();

  useEffect(() => {
    let handler = (e)=>{
      if(!menuRef.current.contains(e.target)){
        setOpen(false);
        console.log(menuRef.current);
      }      
    };

    document.addEventListener("mousedown", handler);
    

    return() =>{
      document.removeEventListener("mousedown", handler);
    }

  });

  return (
    <div className="App">
      <div className='menu-container' ref={menuRef}>
        <div className='menu-trigger' onClick={()=>{setOpen(!open)}}>
          <img src={alsong}></img>
        </div>

        <div className={`dropdown-menu ${open? 'active' : 'inactive'}`} >
        <ul>
          <Button  title="살까 말까"
                     onClick={navigateHome}
                     />
              
                   <Button   title="갈까 말까"
                       onClick={navigateHome}
                />
                   <Button   title="할까 말까"            
                    onClick={navigateHome}
                />
                   <Button   title="룰렛 돌리기"           
                     onClick={navigateHome}
                />
                   <Button   title="MY PAGE"            
                   onClick={navigateHome}
                />
                </ul>
        </div>
      </div>
    </div>
  );
}



export default Drop;