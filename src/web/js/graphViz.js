let config = {
    delimiter: "",	// auto-detect
    newline: "",	// auto-detect
    quoteChar: '"',
    header: true,
    dynamicTyping: true,
    preview: 0,
    encoding: "",
    worker: false,
    comments: false,
    step: undefined,
    complete: undefined,
    error: undefined,
    download: false,
    skipEmptyLines: false,
    chunk: undefined,
    fastMode: undefined,
    beforeFirstChunk: undefined,
    withCredentials: undefined
};

const userField = "user";
const parentIdField = "idParent";
const articleIDField = "Id_Article";


let articles = [];
let idPosition = 1;
let data = [];

let lolzActive = true;


function loadData(f, callback) {


    let myConfig = config;

    myConfig.complete = function (results, file) {
        console.log("Parsing complete:", results, file);


        results.data.forEach((r)=> {
            //find all articles
            let articleID = r[articleIDField];
            let articleAlreadyExists = articles.filter(a=> a == articleID).length != 0;
            if (!articleAlreadyExists) articles.push(articleID);
            data.push(r);
        });

        callback()


    };
    Papa.parse(f, myConfig)

}


function createNetwork(articleFilter) {

    let nodesArray = [{id: 1, label: "null"}];
    let edgesArray = [];

    data.filter(v=>v[articleIDField] == articleFilter).forEach(function (r) {
        //check if r.user is in the nodesArray
        if (nodesArray.filter(r1=>r1.label == r[userField]).length == 0) {
            idPosition++;
            nodesArray.push({id: idPosition, label: r[userField]});
        }

    });
    data.filter(v=>v[articleIDField] == articleFilter).forEach(function (r) {
        let idFrom = nodesArray.filter(function (r1) {
            return r1.label == r[parentIdField]
        })[0].id;
        let idTo = nodesArray.filter(function (r1) {
            return r1.label == r[userField]
        })[0].id;
        edgesArray.push({from: idFrom, to: idTo});
    });

    let nodes = new vis.DataSet(nodesArray);
    let edges = new vis.DataSet(edgesArray);
    let d = {
        nodes: nodes,
        edges: edges
    };
    // create a network
    let container = document.getElementById('network');
    let options = {
        layout: {
            hierarchical: {
                direction: "UD",
                sortMethod: "directed",
                nodeSpacing: 30
            }
        },
        interaction: {dragNodes: true},
        physics: {
            enabled: false
        },
        configure: {
            filter: function (option, path) {
                return path.indexOf('hierarchical') !== -1;

            },
            showButton: false
        }
    };
    let network = new vis.Network(container, d, options);

    container.className = lolzActive ? "lolz" : "";
    $("#infos").show();
    $("#infos #noArticle").text(articleFilter);
    $("#infos #nbNodes").text(nodesArray.length);
    $("#infos #nbEdges").text(edgesArray.length);

}


let onFileChanged = function (files) {
    loadData(files[0], actualizeSelect);

};

const actualizeSelect = function () {
    let option = '';
    for (let i = 0; i < articles.length; i++) {
        option += '<option value="' + articles[i] + '">' + articles[i] + '</option>';
    }
    $('#filterSelect').append(option);
}

let loadButtonAction = function () {

    let filter = $("#filterSelect")[0].value;


    if (filter.length != 0 && data.length != 0) {
        createNetwork(filter);
        promptGraph(filter);
    }

}


let promptGraph = function (articleFilter) {


    $("#visualization").empty();
    var container = document.getElementById('visualization');

    let sortedData = data.filter(v=>v[articleIDField] == articleFilter)
        .map((d)=>d.timeStamp)
        .sort((a, b)=>a - b);


    var items = [];
    var count = 0;
    sortedData.forEach((r)=> {
        var date = new Date(r * 1000);

        var month = date.getMonth();
        var year = date.getYear() + 1900;
        var day = date.getDay();
        var str_date = year + "-" + month + "-" + day;
        var existingCoord = items.find((i)=>i.x == str_date);
        if (!existingCoord) {
            items.push({x: year + "-" + month + "-" + day, y: 1});
        }
        else {
            existingCoord.y ++;
        }

    });
    console.log(items);
    var dataset = new vis.DataSet(items);
    var options = {
        start: items[0].x,
        end: items[items.length - 1].x
    };
    var graph2d = new vis.Graph2d(container, dataset, options);
}
