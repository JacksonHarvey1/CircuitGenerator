function printAndGate(hor, vert, input1, input2) {
    let radius = 20;
    let x = hor+50;
    let y = vert + 50;
    // Draw rotated semi-circle for AND gate
    ctx.beginPath();
    ctx.arc(x, canvas.height - y, radius, Math.PI * 1.5, Math.PI * 0.5);

    // Draw output line
    let outputLineX = x + radius;
    ctx.moveTo(outputLineX, canvas.height - y);
    ctx.lineTo(outputLineX + 20, canvas.height - y);

    // Draw input lines
    let inputLineY1 = canvas.height - y + radius / 2; // Adjusting y-coordinate
    let inputLineY2 = canvas.height - y - radius / 2; // Adjusting y-coordinate
    ctx.moveTo(x - radius / 2 + 10, inputLineY1);
    ctx.lineTo(x - radius, inputLineY1);
    ctx.moveTo(x - radius / 2 + 10, inputLineY2);
    ctx.lineTo(x - radius, inputLineY2);

    ctx.closePath();

    ctx.fillStyle = 'rgba(0, 0, 255, 0.5)';
    ctx.fill();
    ctx.stroke();

    // Draw input labels slightly higher and lower
    ctx.font = '12px Arial';
    ctx.fillStyle = 'black';
    ctx.fillText(input1, x - radius / 2 - 20, inputLineY2 - 6);
    ctx.fillText(input2, x - radius / 2 - 20,  inputLineY1 + 16);
}
function printOrGate(hor, vert, input1, input2) {
    let x = hor+50;
    let y = vert + 50;
    let radius = 20;
    let endAngle = Math.PI * 0.5;
    let startAngle = Math.PI * 1.5;

    // Draw OR gate shape
    ctx.beginPath();
    ctx.arc(x - radius * 0.5, canvas.height - y, radius, startAngle, endAngle, false); // Adjusting y-coordinate
    ctx.lineTo(x + radius * 0.5, canvas.height - (y - radius));  // Bottom of the second arc
    ctx.arc(x + radius * 0.5, canvas.height - y, radius, endAngle, startAngle, true); // Adjusting y-coordinate
    ctx.lineTo(x - radius * 0.5, canvas.height - (y + radius));  // Top of the second arc
    ctx.fillStyle = 'rgba(255, 0, 0, 0.5)';
    ctx.closePath();  // Close the path before fill
    ctx.fill();
    ctx.stroke();

    // Draw input lines
    ctx.beginPath();
    let inputLineY1 = canvas.height - (y + radius*0.5);
    let inputLineY2 = canvas.height - (y - radius*0.5);
    ctx.moveTo(x - radius * 0.5 - 7 , inputLineY1);
    ctx.lineTo(x - radius * 0.5 + 17, inputLineY1);
    ctx.moveTo(x - radius * 0.5 - 7, inputLineY2);
    ctx.lineTo(x - radius * 0.5 + 17, inputLineY2);
    ctx.strokeStyle = 'rgba(0, 0, 0, 0.5)';
    ctx.stroke();

    // Draw input labels slightly higher and lower
    ctx.font = '12px Arial';
    ctx.fillStyle = 'black';
    ctx.fillText(input1, x - radius * 0.5 - 20, inputLineY1 - 6);
    ctx.fillText(input2, x - radius * 0.5 - 20, inputLineY2 + 16);

    // Draw output line
    ctx.beginPath();
    let outputLineX = x + radius + 30;
    let outputLineY = canvas.height - y;
    ctx.moveTo(x + radius*1.5, outputLineY);
    ctx.lineTo(outputLineX, outputLineY);
    ctx.stroke();
}
function connectLines(gates) {

    // Iterate through the gates and connect lines based on their relationships
    for (let i = 1; i < gates.length; i++) {
        let currentGate = gates[i];

        for (let j = 0; j < i; j++) {

            let previousGate = gates[j];
            // Declare variables outside of the if-else blocks with default values
            let pStartX =0, pStartYTop = 0, pStartYBottom = 0, pEndX = 0, pEndY = 0;
            let cStartX = 0, cStartYTop = 0, cStartYBottom = 0, cEndX =0, cEndY = 0;


            // Check if the gates have matching inputs and outputs
            if (previousGate.booleanOut.includes(currentGate.operand1)) {
                console.log("Previous Gate is", previousGate.gateType);
                console.log("Current Gate is", currentGate.gateType);
                console.log("Condition 1 met");
                if (previousGate.gateType == "OR_GATE") {
                    console.log("Previous Gate is OR_GATE")
                    // Update values for OR_GATE
                    //let inputLineY1 = canvas.height - (y + radius*0.5);
                    //let inputLineY2 = canvas.height - (y - radius*0.5);
                    pStartX = previousGate.horizontalSpacing+50 + 50 - 10 - 7;//or +17
                    pStartYTop = canvas.height - (750-previousGate.verticalSpacing + 50 + 10);
                    pStartYBottom = canvas.height - (750-previousGate.verticalSpacing + 50 - 10);
                    pEndX = previousGate.horizontalSpacing+50+50 + 30 + 20;
                    pEndY = canvas.height - (750-previousGate.verticalSpacing + 50);
                } if (currentGate.gateType == "OR_GATE") {
                    console.log("Current Gate is OR_GATE");
                    // Update values for OR_GATE
                    cStartX = currentGate.horizontalSpacing+50 + 50 - 10 -7;//or +17
                    cStartYTop = canvas.height - (750-currentGate.verticalSpacing + 50 + 10);
                    cStartYBottom = canvas.height - (750-currentGate.verticalSpacing + 50 - 10);
                    cEndX = currentGate.horizontalSpacing+50 + 30 + 20;
                    cEndY = canvas.height - (750-currentGate.verticalSpacing + 50);
                }  if (previousGate.gateType == "AND_GATE") {
                    console.log("Previous Gate is AND_GATE");
                    // Update values for AND_GATE
                    //let outputLineX = x + radius;
                    //let inputLineY1 = canvas.height - y + radius / 2; // Adjusting y-coordinate
                    //let inputLineY2 = canvas.height - y - radius / 2; // Adjusting y-coordinate
                    //ctx.moveTo(x - radius / 2 + 10, inputLineY1);
                    pStartX = previousGate.horizontalSpacing + 50 - 10 - 10;
                    pStartYTop = canvas.height - (750-previousGate.verticalSpacing + 50) + 10;
                    pStartYBottom = canvas.height - ((750-previousGate.verticalSpacing + 50) - 10);
                    pEndX = previousGate.horizontalSpacing+50+20 + 20 + 50;
                    pEndY = canvas.height -(750- previousGate.verticalSpacing+ 50);
                }  if (currentGate.gateType == "AND_GATE") {
                    console.log("Current Gate is AND_GATE");
                    // Update values for AND_GATE
                    cStartX = currentGate.horizontalSpacing +80;
                    cStartYTop = canvas.height - (750-currentGate.verticalSpacing + 50) - 10;
                    cStartYBottom = canvas.height - ((750-currentGate.verticalSpacing + 50) + 10);
                    cEndX = currentGate.horizontalSpacing+50+20 + 20 + 50;
                    cEndY = canvas.height -(750- currentGate.verticalSpacing+ 50);
                }
                drawLines(pEndX, pEndY, cStartYTop, cStartX, previousGate, currentGate);

            } 
            else if (previousGate.booleanOut.includes(currentGate.operand2)) {
                console.log("Previous Gate is", previousGate.gateType);
                console.log("Current Gate is", currentGate.gateType);
                console.log("Condition 2 met");
                //console.log(previousGate);
                //console.log(currentGate);
                if (previousGate.gateType == "OR_GATE") {
                    console.log("Previous Gate is OR_GATE");
                    // Update values for OR_GATE
                    pStartX = previousGate.horizontalSpacing+50 + 50 - 10 - 7;//or +17
                    pStartYTop = canvas.height - (750-previousGate.verticalSpacing + 50 + 10);
                    pStartYBottom = canvas.height - (750-previousGate.verticalSpacing + 50 - 10);
                    pEndX = previousGate.horizontalSpacing+50+50 + 30 + 20;
                    pEndY = canvas.height - (750-previousGate.verticalSpacing + 50);
                }  if (currentGate.gateType == ("OR_GATE")) {
                    console.log("Current Gate is OR_GATE");
                    // Update values for OR_GATE
                    cStartX = currentGate.horizontalSpacing+50 + 50 - 10 -7;//or +17
                    cStartYTop = canvas.height - (750-currentGate.verticalSpacing + 50 + 10);
                    cStartYBottom = canvas.height - (750-currentGate.verticalSpacing + 50 - 10);
                    cEndX = currentGate.horizontalSpacing+50 + 30 + 20;
                    cEndY = canvas.height - (750-currentGate.verticalSpacing + 50);
                }  if (previousGate.gateType == "AND_GATE") {
                    console.log("Previous Gate is AND_GATE");
                    // Update values for AND_GATE
                    pStartX = previousGate.horizontalSpacing + 80;
                    pStartYTop = canvas.height - (750-previousGate.verticalSpacing + 50) + 10;
                    pStartYBottom = canvas.height - ((750-previousGate.verticalSpacing + 50) - 10);
                    pEndX = previousGate.horizontalSpacing+50+20 + 20 + 50;
                    pEndY = canvas.height -(750- previousGate.verticalSpacing+ 50);
                }  if (currentGate.gateType == ("AND_GATE")) {
                    console.log("Current Gate is AND_GATE");
                    // Update values for AND_GATE
                    cStartX = currentGate.horizontalSpacing +80;
                    cStartYTop = canvas.height - (750-currentGate.verticalSpacing + 50) + 10;
                    cStartYBottom = canvas.height - ((750-currentGate.verticalSpacing + 50) - 10);
                    cEndX = currentGate.horizontalSpacing+50+20 + 20 + 50;
                    cEndY = canvas.height -(750- currentGate.verticalSpacing+ 50);
                }
            }
            drawLines(pEndX, pEndY, cStartYBottom, cStartX, previousGate, currentGate);
        }
    }
}


function drawLines(startX, startY, endY, endX, previousGate, currentGate) {
console.log("Drawing lines", startX, startY, endX, endY);
    if (previousGate.horizontalSpacing<=currentGate.horizontalSpacing) {
    // Draw the first horizontal line
    ctx.beginPath();
    ctx.moveTo(startX, startY);
    ctx.lineTo(endX, startY);
    ctx.strokeStyle = 'rgba(225, 0, 0, 0.5)';
    ctx.stroke();

    // Draw the vertical line connecting the two gates
    ctx.beginPath();
    ctx.moveTo(endX, startY);
    ctx.lineTo(endX, endY);
    ctx.strokeStyle = 'rgba(0, 0, 0, 0.5)';
    ctx.stroke();
    }
    else if (previousGate.horizontalSpacing>=currentGate.horizontalSpacing) {
    // Draw the extensions
    ctx.beginPath();
    ctx.moveTo(startX, startY);
    ctx.lineTo(startX, startY-40);
    ctx.strokeStyle = 'rgba(225, 0, 0, 0.5)';
    ctx.stroke();
    // Draw the first horizontal line
    ctx.beginPath();
    ctx.moveTo(startX, startY-40);
    ctx.lineTo(endX, startY-40);
    ctx.strokeStyle = 'rgba(225, 0, 0, 0.5)';
    ctx.stroke();

    // Draw the vertical line connecting the two gates
    ctx.beginPath();
    ctx.moveTo(endX, startY-40);
    ctx.lineTo(endX, endY);
    ctx.strokeStyle = 'rgba(0, 0, 0, 0.5)';
    ctx.stroke();
    }
}


