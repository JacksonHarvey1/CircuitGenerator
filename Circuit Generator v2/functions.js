document.addEventListener('DOMContentLoaded', function() {
    const generateButton = document.getElementById('generateButton');
    generateButton.addEventListener('click', generateCircuit);

    function generateCircuit() {
        const equation = document.getElementById('equation').value;
        const gates = parseEquation(equation);
        renderGates(gates);
    }

    function parseEquation(equation) {
        const gates = [];
        let currentGate = null;
        let currentInput = '';
    
        for (let i = 0; i < equation.length; i++) {
            const char = equation[i];
    
            if (char === '+') {
                if (currentInput) {
                    gates.push({ type: 'input', value: currentInput });
                    currentInput = '';
                }
                currentGate = { type: 'and', inputs: [] };
                gates.push(currentGate);
            } else if (char === '*') {
                if (currentInput) {
                    gates.push({ type: 'input', value: currentInput });
                    currentInput = '';
                }
                currentGate = { type: 'or', inputs: [] };
                gates.push(currentGate);
            } else if (char === '\'') {
                currentInput += '\'';
            } else {
                currentInput += char;
            }
        }
    
        if (currentInput) {
            gates.push({ type: 'input', value: currentInput });
        }
    
        return gates;
    }
    


    function renderGates(gates) {
        const svg = d3.select('#circuit');
        svg.selectAll('*').remove(); // Clear previous diagram
    
        const inputGates = gates.filter(gate => gate.type === 'input');
        const otherGates = gates.filter(gate => gate.type !== 'input');
    
        const maxInputWidth = Math.max(...inputGates.map(gate => gate.value.length)) * 10 + 40;
        const gateHeight = 60;
        const gateWidth = 100;
        const gateSpacing = 40;
    
        // Render input gates
        const inputGateGroups = svg.selectAll('.input-gate')
            .data(inputGates)
            .enter()
            .append('g')
            .attr('class', 'input-gate')
            .attr('transform', (d, i) => `translate(${maxInputWidth / 2}, ${(i + 1) * (gateHeight + gateSpacing)})`);
    
        inputGateGroups.append('rect')
            .attr('width', d => d.value.length * 10 + 20)
            .attr('height', gateHeight)
            .attr('rx', 5)
            .attr('ry', 5)
            .attr('fill', 'white')
            .attr('stroke', 'black');
    
        inputGateGroups.append('text')
            .attr('x', d => (d.value.length * 10 + 20) / 2)
            .attr('y', gateHeight / 2 + 5)
            .text(d => d.value);
    
        // Render other gates
        const otherGateGroups = svg.selectAll('.gate')
            .data(otherGates)
            .enter()
            .append('g')
            .attr('class', 'gate')
            .attr('transform', (d, i) => `translate(${maxInputWidth + (i + 1) * (gateWidth + gateSpacing)}, ${inputGates.length * (gateHeight + gateSpacing) + gateHeight / 2})`);
    
        otherGateGroups.append('rect')
            .attr('width', gateWidth)
            .attr('height', gateHeight)
            .attr('rx', 5)
            .attr('ry', 5)
            .attr('fill', 'white')
            .attr('stroke', 'black');
    
        otherGateGroups.append('text')
            .attr('x', gateWidth / 2)
            .attr('y', gateHeight / 2 + 5)
            .text(d => d.type === 'and' ? 'AND' : 'OR');
    
        // Render connections
        const lineGenerator = d3.line()
            .x(d => d.x)
            .y(d => d.y)
            .curve(d3.curveBasis);
    
        const connections = [];
        for (let i = 0; i < otherGates.length; i++) {
            const gate = otherGates[i];
            const gateX = maxInputWidth + (i + 1) * (gateWidth + gateSpacing);
            const gateY = inputGates.length * (gateHeight + gateSpacing) + gateHeight / 2;
    
            for (let j = 0; j < gate.inputs.length; j++) {
                const input = gate.inputs[j];
                const inputIndex = inputGates.findIndex(g => g === input);
                const inputY = (inputIndex + 1) * (gateHeight + gateSpacing);
                const inputX = maxInputWidth / 2 + (input.value.length * 10 + 20);
                const points = [
                    { x: gateX, y: gateY },
                    { x: inputX, y: gateY },
                    { x: inputX, y: inputY + gateHeight / 2 },
                    { x: maxInputWidth / 2, y: inputY + gateHeight / 2 }
                ];
                connections.push(points);
            }

        }
        console.log("COnntecit lines", connections)

        svg.selectAll('.connection')
            .data(connections)
            .enter()
            .append('path')
            .attr('class', 'connection')
            .attr('d', d => lineGenerator(d))
            .attr('stroke', 'black')
            .attr('stroke-width', 2)
            .attr('fill', 'none');
    }});