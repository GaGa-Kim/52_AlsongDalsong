import React, { useState } from "react";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";
import { BiTrash, BiGridVertical, BiPlus } from "react-icons/bi";
import { v4 as uuidv4 } from "uuid";
import Roulette from "./Roulette";

const FormularioTexto = () => {
  const [inputList, setInputList] = useState([
    {
      id: uuidv4(),
      text: "Где туалет?"
    },
    {
      id: uuidv4(),
      text: "Можно вкусную жижу"
    },
    {
      id: uuidv4(),
      text: "А стики есть?"
    },
    {
      id: uuidv4(),
      text: "А стики есть?"
    },
    
  
  
  ]);
  // handle input change
  const handleInputChange = (e, index) => {
    const { name, value } = e.target;
    const list = [...inputList];
    list[index][name] = value;
    setInputList(list);
  };

  // handle click event of the Remove button
  const handleRemoveClick = (index) => {
    const list = [...inputList];
    list.splice(index, 1);
    setInputList(list);
  };

  // handle click event of the Add button
  const handleAddClick = () => {
    setInputList([...inputList, { text: "", id: uuidv4() }]);
  };

  function handleOnDragEnd(result) {
    if (!result.destination) return;

    const items = Array.from(inputList);
    const [reorderedItem] = items.splice(result.source.index, 1);
    items.splice(result.destination.index, 0, reorderedItem);

    setInputList(items);
  }

  return (
    <div className="main-form">
      <div className="text-title">
        <h2></h2>
      </div>
      {/*  */}
      <Roulette data={inputList} />
      <DragDropContext onDragEnd={handleOnDragEnd}>
        <Droppable droppableId="items">
          {(provided) => (
            <ul
              className="items"
              {...provided.droppableProps}
              ref={provided.innerRef}
              style={{ listStyle: "none" }}
            >
              {inputList.map((x, index) => {
                return (
                  <Draggable key={x.id} draggableId={x.id} index={index}>
                    {(provided) => (
                      <li
                        ref={provided.innerRef}
                        {...provided.draggableProps}
                        {...provided.dragHandleProps}
                        className="list-item"
                      >
                        <div className="item">
                          <BiGridVertical />
                          <input
                            name="text"
                            placeholder="Введи что-нибудь(или нет)"
                            value={x.text}
                            onChange={(e) => handleInputChange(e, index)}
                            className="input"
                          />
                          <div className="btn-box">
                            {inputList.length !== 1 && (
                              <button
                                className="button"
                                onClick={() => handleRemoveClick(index)}
                              >
                                <BiTrash />
                              </button>
                            )}
                          </div>
                        </div>
                      </li>
                    )}
                  </Draggable>
                );
              })}
              {provided.placeholder}
            </ul>
          )}
        </Droppable>
      </DragDropContext>
      <button
        onClick={handleAddClick}
        style={{ marginLeft: "2.1rem" }}
        className="button"
      >
        <BiPlus />
      </button>
    </div>
  );
};

export default FormularioTexto;
