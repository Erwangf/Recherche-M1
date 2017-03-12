let defaultConfig = {
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



function loadData(f,callback){


    let myConfig = defaultConfig;

    myConfig.complete =  function(results, file) {
        console.log("Parsing complete:", results, file);


        results.data.forEach((r)=>{
            //find all articles
            let articleID = r[articleIDField];
            let articleAlreadyExists = articles.filter(a=> a==articleID).length!=0;
            if(!articleAlreadyExists) articles.push(articleID);
            data.push(r);
        });

        callback()


    };
    Papa.parse(f, myConfig)

}



function createNetwork(articleFilter){
    let nodesArray = [{id:1,label:"null"}];
    let edgesArray = [];

    data.filter(v=>v[articleIDField]==articleFilter).forEach(function(r){
        //check if r.user is in the nodesArray
        if(nodesArray.filter(r1=>r1.label==r[userField]).length==0){
            idPosition++;
            nodesArray.push({id:idPosition,label:r[userField]});
        }

    });
    data.filter(v=>v[articleIDField]==articleFilter).forEach(function(r){
        let idFrom = nodesArray.filter(function(r1){return r1.label==r[parentIdField]})[0].id;
        let idTo = nodesArray.filter(function(r1){return r1.label==r[userField]})[0].id;
        edgesArray.push({from:idFrom,to:idTo});
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
                sortMethod: "directed"
            }
        },
        interaction: {dragNodes :false},
        physics: {
            enabled: false
        },
        configure: {
            filter: function (option, path) {
                return path.indexOf('hierarchical') !== -1;

            },
            showButton:false
        }
    };
    let network = new vis.Network(container, d, options);
}

let onFileChanged = function(files){
    loadData(files[0],actualizeSelect);

};

const actualizeSelect = function(){
    let option = '';
    for (let i=0;i<articles.length;i++){
        option += '<option value="'+ articles[i] + '">' + articles[i] + '</option>';
    }
    $('#filterSelect').append(option);
}

let loadButtonAction = function(){

    let filter = $("#filterSelect")[0].value;

    if(filter.length !=0 && data.length!=0){
       createNetwork(filter);
    }

}

