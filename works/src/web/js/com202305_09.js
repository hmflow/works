    
    /*2023-05~ 2023-09 월 프로젝트 기간중 작업한 소스입니다.
      보안관련 코드와 주석은 모두 삭제하였습니다.
     */
    /**
        2023-05-30
        이상근 (YESIAM)    
    
        urzzFunction 공통함수 입니다
        ********.*** 파일에서 기능을 테스트 할수 있습니다.
         * 
    
       1. 그리드 구현 함수들입니다.
        모든것을 한세트로 가져가셔서 구현하시길 바랍니다.
            복사만 : 
                urzzFunction.fnShowAccRow              : 화면에 표시되어지는 그리드라디오을 초기화합니다.
                urzzFunction.fnMoveEvent               : 그리드 화면 이동 버튼의 모든 이벤트를 설정합니다
                urzzFunction.btnPrev_clickEventElement : 좌우 이동버튼이 클릭되어졌을때 실행하는 이벤트 입니다.
                urzzFunction.fnMoveRdPage              : 화면에 표시되어질 아이콘을 설정하는 함수입니다.
            구현 :  
                mainObj.grpRdGrd_onclick              : 그리드버튼이 클릭되었을때 실행 이벤트 
            
        urzzFunction.fnShowAccRow : :화면에 표시되어지는 그리드라디오을 초기화합니다. 
           (  array[json]  : 화면에 표시할 항목을 json 배열로 받습니다.  
            , Integer : 화면에 표시 되어질 개수를 설정합니다  
            , Object  : 그리드 오브젝트 
            , String  : 라벨로 지정되어질 html을 설정합니다.  1번? 파라메터의 키값을 @key형식으로 지정하여 사용합니다.
            , value   : 화면에 표시되어지는 항목의 키값을 지정합니다.
            )
        
        예 : FND000102.xml화면 CIM41100100 콜백 함수를 참조하시길바랍니다 
            var tempHtm = "<span class='tit'>@pdtNm</span><span>@acno</span><span>@dfryPsblAmt</span>" ;
            urzzFunction.fnShowAccRow(jsonData,pageCnt, grpRdGrd , listPaging, tempHtm, "값으로 지정할 컬럼");
             
        
        뷰영역에 아래 코드를 추가합니다.
    --------------------------WebSqure View code--------------------------------------    
        <xf:group class="box-comn type1 account" id="" style="">
            <xf:group class="list-account" id="grpRdGrdComp">
                <xf:select1 appearance="full" class="radio-account" cols="" id="grpRdGrd" ref="" renderType="radiogroup" rows=""
                    selectedIndex="-1" style="" tagname="" userData1="" userData2="" ev:onlabelclick="mainObj.grpRdGrd_onclick"  >
                    <xf:choices></xf:choices>
                </xf:select1>
                <w2:anchor class="btn-list-prev" id="btnPrev" outerDiv="false" style="" ev:onclick="urzzFunction.btnPrev_clickEventElement"> </w2:anchor>
                <w2:anchor class="btn-list-next" id="btnNext" outerDiv="false" style="" ev:onclick="urzzFunction.btnPrev_clickEventElement"> </w2:anchor>
                <w2:generator class="list-paging" id="listPaging" style="" tagname="span">
                    <w2:anchor id="pagingBtn" outerDiv="false" style="" ev:onclick="urzzFunction.btnPrev_clickEventElement"></w2:anchor>
                </w2:generator>
            </xf:group> 
        </xf:group> 
    ---------------------------------------------------------------------------------------     
        
        
      2. 공통함수 : FND 개발에 편의를 주기위한 함수들입니다.
        1) 파라메터 이력조회
        - urzzFunction.fnDataListViewConsole( P1 string taskid
                                         , P2 string search value) 
             설명    : 디버그 모드에서 현재 urzObjInfo.getBizInfo 에서 입력한 정보를 확인할수있습니다.  
             파라메터 : P1 작업아이디 (예 : 'FND00Map')
                      P2 검색어 (생략가능)
             예     : 입력) urzzFunction.fnDataListViewConsole("FND00Map","여부")
                      결과)
                           // 컬럼항목                   값  입력되어진화면             명칭  
                          smsNotiYn                 :                           (SMS통보여부)  
                          hghRkdgPdtChoYn           : 1 [FND000104]             (고위험도상품선택여부_DBC00010102)  
                          nvstorInfoCfrrptWrtDenyYn : 0 [FND000104]             (투자자정보확인서작성거부여부_DBC00010102)  
                          signTrnsYn                :                           (서명거래여부)  
                          discuseAAA               :                           (신분증스캔여부)
                          
                            
         2) 데이터셋 생성
         - urzzFunction.fnDataListViewSetter(P1 string DataSetId
                                         ,P2 string taskid
                                         ,P3 object 생성화면)
             설명    : urzObjInfo.getBizInfo(taskid) 에 출력되어지는 항목들의 DataSet을 생성합니다. (Object,function 제외)
             파라메터 : P1 DataSet 의 ID를 입력합니다.
                      P2 urzObjInfo.getBizInfo("") 데이터를 호출할 taskId를 입력합니다  (예 : 'FND00Map')
                      P3 화면의 Object 를 입력합니다. mainObj.$w 를 고정으로 사용합니다.
             예     : urzObjInfo.getBizInfo("resultDataList01", "FND00Map", mainObj.$w); 
             
         
         3) 라디오 버튼 항목설정
         - urzzFunction.fnRdBtnSetter(P1 json {value:label}  
                                  ,P2 object (xf:select1, renderType="radiogroup")
                                  ,P3 boolean OrderBy(true:asc, false:desc)
                                  ,P4 string Defalut Selected   )
             설명    :   (xf:select1, renderType="radiogroup") 항목에 선택버튼을 생성합니다.
             파라메터 : P1 json  입력할 데이터를 JSON Object 로 생성합니다.  예 {'00': "없음",'CDO' : "부채담부보부증권", 'CDS' : "신용부도스왑"}
                      P2 적용할 Object입니다.
                      P3 정렬할 순서를 지정합니다. true, false 정렬 순서를 정합니다.
                      P4 생성후에 지정되어질 항목을 지정합니다. 
                     
             예     : var rdJson = {'00': "없음",'CDO' : "부채담부보부증권", 'CDS' : "신용부도스왑"};
                     urzzFunction.fnRdBtnSetter(rdJson, lbKind, false, '00'); 
    
          4) 데이터셋에 파라메터 세팅, 입력혹은 변경되어진 항목의 이력을 남겨줍니다.
          - urzzFunction.fnSetBizDataMerge(P1 json {column:value}
                                      , P2 string TasId
                                      , P3 string PageId 
                                      , P4 [isUndefined] json {column:name})
             설명    : 화면과 화면을 P1 기준으로 urzObjInfo.dataInfo에 파라메터를 입력하여주며 신규혹은 값이 재정의 되어질경우 
                     변경되어진 해당 화면정보를 데이터맵생성이나 이력조회시 사용할수 있도록 저장합니다. 
             파라메터 : P1 urzObjInfo.setBizinfo 할 데이터
                      P2 작업아이디
                      P3 페이지 아이디
                      P4 (제외가능) 입력되어진 파라메터의 정보 한글명칭을 입력합니다 
             예     : var rdJson = {'guestNm': "자바왕",'infoSujDvcd' : "01, 'resLamt' : "1000000000000"};
                     urzzFunction.fnSetBizDataMerge(rdJson, "FND00Map", "FND000101", {guestNm : "고객명"}) 
                 
         5) 포멧과 날자 데이터를 입력하여 출력합니다.    
          - urzzFunction.fnFormatToDateTime(P1 string Foramt
                                        ,P2 string|number Date)
             파라메터 : P1 포멧문자열
                      P2 날자 데이터
             예     : urzzFunction.fnFormatToDateTime("YYYY-MM-DD HH-mi-ss", "20230612123022")
    */ 
    var urzzFunction= {
             
    };
    urzzFunction.mainObj = null;
    urzzFunction.isTest = false; 
          
//#######################################################################
//화면그리드 표시영억
//####################################################################### 
    urzzFunction.fnShowAccRow = function(EV_IPCs, rowCnt, grdObj ,listPaging ,labelHtml , value){
        // 라디오그리드 컴포넌트의 항목을 모두 삭제합니다.
        urzzFunction.fnMoveRdPage(0);
        if(EV_IPCs.length == 0){
            urzObjInfo.hideLoading();
            urzObjInfo.alert("조회되어진 정보가 없습니다");
            return;
        }
        grdObj.removeAll();
        listPaging.removeChild();
        // labelHtml 에설정되어진 항목을 가져와서 데이터 셋별로 라벨을 설정합니다.
        EV_IPCs.forEach(function(eachJs){
            // 입력받은 템플릿 라벨 표시
            var setTag = labelHtml;
            // @id 의 항목이 있을경우 값을 replace해줍니다.
            var keysRep = Object.keys(eachJs).sort(function(a,b){return b.length -a.length;});

            keysRep.forEach(function(key){
                var changeKey ="@"+key;
                setTag = setTag.indexOf(changeKey) != -1 ? setTag.replace(changeKey , eachJs[key]) :setTag ;
            });
            // 버튼항목을 추가합니다. 
            grdObj.addItem(eachJs[value], setTag ,EV_IPCs.indexOf(eachJs));
        });     
        // 한줄에 몇개를 표시할지 지정합니다. 입력받은 개수/2 를 하여 지정하고 짝수로만 가능합니다.
        var rowCntSet = rowCnt / 2;
        $("#" + grdObj.id).css("grid-template-columns", "repeat("+rowCntSet+",1fr)")
        // 임시로 class 항목에 rowcnt_숫자 형식으로 항목을 표시합니다.
        $('.list-account>div').addClass("rowcnt_" +rowCnt);
        if(EV_IPCs.length <= 0) return;
        // 하단 항목에 설정된 페이지 목록을 설정하여 이동합니다.
        // 이후 조회시 해당 항목 기준으로 아이템을 표시합니다  
        for(var pagInt =0; pagInt < EV_IPCs.length / rowCnt; pagInt ++){
            listPaging.insertChild(pagInt, pagInt); 
        }; 
        listPaging.visible($('.list-paging>a').length == 1 ? false : true); 
        // 초기화시 모든 페이지 설정을 비활성화 합니다.
        urzzFunction.fnMoveRdPage(0);
        // 페이지 이동 이벤트 실행
        urzzFunction.fnMoveEvent(0);
    };
    
    
    /**
        2023-05-30
        이상근 (YESIAM)  
        그리드 화면 이동 버튼의 모든 이벤트를 설정합니다.
    */  
    urzzFunction.fnMoveEvent = function(idx){  
        // class 에 임시로 저장한 콤보로우의 개수를 호출합니다
        var rowCnt = $($('.list-account>div')[0]).attr("class").split("rowcnt_")[1] *1;
        // 시작 인덱스를 지정합ㄴ디다
        var stIdx = idx*rowCnt;
        // 모든항목을 가린후 표실할 항목만 block합니다.
        $('.list-account>div>.w2radio_item').css("display","none");
        for(var i = stIdx ;  i< stIdx +rowCnt  ; i++ ){
            $('.list-account>div>.w2radio_item_'+i).css("display","block");    
        }
        urzzFunction.fnMoveRdPage(idx);
    } 
    /**
        2023-05-30
        이상근 (YESIAM)  
        이동버튼이 클릭되어졌을 이벤트입니다
    */  
    urzzFunction.btnPrev_clickEventElement = function() {
        // 현재 인덱스 변수설정
        var nowSelIdx = 0;
        // 페이지 오브젝트 호출
        var pagingObj = $('.list-paging>a');
        // 페이지 오브젝트에 선택되어진 항목의 위치를 nowSelIdx 에 저장합니다.
        pagingObj.each(function(index){
            if($(this).attr("class").split(/\s/g).indexOf("on") != -1){
                nowSelIdx = index*1;
            }
        });
        // 현재 선택되어진 아이디를 호출
        var id = this.org_id;
        // 하단측 점모양 이동버튼 선택하였을경우
        if(id.indexOf("listPaging_") != -1){
            // 하단 이동아이콘의 클릭되어진 항목을 가져옴
            var selIdx = id.split(/_/g)[1];
            // 이동아이콘이 변경되었다면 데이터 호출을 합니다.
            if(selIdx != nowSelIdx){
                urzzFunction.fnMoveEvent(selIdx); 
            }
        }else{
            //클릭되어진 버튼 가져오기
            var addIdx =  id == "btnNext" ? 1 : -1;
            // 이동버튼 클릭시 이전 버튼 클릭하였을 경우 0보다 크지 않으면 -1을하고 다음버튼을클릭하였을경우 전체 페이지수자보다 적으면 +1을 합니다.
            var selIdx = nowSelIdx+addIdx <=0 ? 0 : nowSelIdx+addIdx > pagingObj.length-1 ?  pagingObj.length -1 : nowSelIdx+addIdx;
            // 페이지 이동
            urzzFunction.fnMoveEvent(selIdx); 
        }
    };
    /**  
        2023-05-30
        이상근 (YESIAM)  
        화면에 표시되어질 아이콘을 설정하는 함수입니다.
    */  
    urzzFunction.fnMoveRdPage = function(idx){ 
        // 초기화시 모든 페이지 설정을 비활성화 합니다.
        var pagObj = $('.list-paging>a');
        pagObj.removeClass("on");
        // 현재 인덱스를 활성화 합니다.
        $(pagObj[idx]).addClass("on");
        // 인덱스가0번일경우 뒤로버튼 비활성화 , 인덱스가 전체 페이지-1 보다 크거나 같을경우 다음버튼 비활성화
        var isFstIdx = idx == 0 ? "hidden" : "visible";
        var islstIdx = idx >= pagObj.length-1 ?  "hidden"  : "visible" ;
        $(".btn-list-prev").css("visibility", isFstIdx );
        $(".btn-list-next").css("visibility", islstIdx );
        urzzFunction.fnMoveRdPageEvent();
    }
    
    urzzFunction.fnMoveRdPageEvent = function(){}
//#######################################################################
//공통일반영역
//#######################################################################
    //공통코드 호출 데이터 입니다. [0] invo데이터셋 세팅용 정보 [1] 결과값 호출 데이터셋     
    urzzFunction.cdOptions =[{"id"          : "ComCdMapInVo" 
                          ,"type"        : "EV_IPCMap"
                          ,"option"      : {"baseNode":"map"} 
                          ,"keyInfo"     : [ {"id":"cdDomnEnm"   ,"name" : "cdDomnEnm"   ,"EV_IPCType":"text"}
                                           , {"id":"cdClsfTypeNo","name" : "cdClsfTypeNo","EV_IPCType":"text"}]}
                         ,{"id"          : "ComCdListVo" 
                          ,"type"        : "EV_IPCList" 
                          ,"option"      : {"baseNode": "list" , "repeatNode": "map" } 
                          ,"columnInfo"  : [ {"id" : "cdDomnEnm", "name" : "코드그룹","EV_IPCType" :"text"}
                                           , {"id" : "code"     , "name" : "코드"   ,"EV_IPCType" :"text"}
                                           , {"id" : "name"     , "name" : "라벨"   ,"EV_IPCType" :"text"}]}
                         ];
    urzzFunction.options  = {  "id"         : "codeInfo"
                          , "ref"        : "EV_IPC:json,{\"id\":\"daaaata\",\"key\":\"InVo\"}"
                          , "target"     : "EV_IPC:json,dataaa"
                          , "action"     : "/common/code"
                          , "method"     : "post"
                          , "mediatype"  : "application/json"
                          , "encoding"   : "UTF-8"
                          , "mode"       : "synchronous"
                          , "processMsg" : ""
                         }   
    /*  
    콘솔조회       (fnDataListViewConsole)     
    데이터셋 생성 (fnDataListViewSetter) :: 디버그 모드에서 아래코드를 실행하면 데이터셋이 생성 되어집니다.
    컬럼이 최종사용되어진 화면 호출 설정  (fnLastInstView)  최종 사용되어진 페이지 항목을 입력합니다.
    라디오박스에 컴포넌트 세팅 :svngKndDvcdJson        
    공통데이터 셋 (fnSetBizDataMerge)  urzObjInfo.setBiz 에 데이터를 설정합니다. 
     
    */  
    
    /**
        2023-05-22
        이상근 (YESIAM)  
        라디오버튼을 생성후 첫번째 항목에 포커스를 줍니다.
        param  jsonInfo : json (라디오버튼 항목을 {코드:라벨} 형식으로 입력되어져 있습니다). 
               rdObj    : object (라디오버튼 객체 입니다.)
               boolean 
               or Array : boolean : true ? 오름차순 :내림차순
                            Array : 입력된 순서를 입력합니다.   
                      string 포커스 디폴트값
    */
    urzzFunction.fnRdBtnSetter = function(jsonInfo, rdObj, orderby, focusValDflt){ 
        var jsonInfoArr = Array.isArray(orderby) ? orderby : Object.keys(jsonInfo).sort(function(a, b){return (orderby ? a-b:b-a)});
        jsonInfoArr.forEach(function(key){
            rdObj.addItem(key, jsonInfo[key]);
        }); 
        var focusVal = focusValDflt == undefined || focusValDflt== null ? "" : focusValDflt;
        //첫번? 항목 포커스
        rdObj.setValue(focusVal == "" ? jsonInfoArr[orderby ? jsonInfoArr.length-1 : -1] : focusVal);
    }
    
    
    /**
        2023-05-31
        이상근 (YESIAM) 
        데이터 컬렉션 생성 함수.   
               ( String  : 데이터셋 아이디
            , Object : mainObj.$w
            )
            return json
            리턴받은 값을 아래 함수에 실행시켜줍니다
    */
    urzzFunction.fnDataListViewSetter = function(id,taskId,wObject){
        var colInfo = [];
        // ** 로 사용하기때문에 에 그냥 하드코딩 컬럼정보 호출
        var jsonData = urzObjInfo.getBizInfo(taskId);
        // 컬럼정보 호출
        var conInfo  =jsonData["columnInfo"]
        // 컬럼셋팅을 합니다.
        Object.keys(jsonData).forEach(function(key){
            colInfo.push({id: key , name : conInfo[key] , EV_IPCType :"text", value :jsonData[key] == undefined ? "" :jsonData[key] });
        });
        wObject.EV_IPC.create({ "id"      : id
                               , "type"    : "EV_IPCMap"
                            , "option"  : {"baseNode" :"map"}
                            , "keyInfo" : colInfo     });
    }
    
    /**
        2023-05-19
        이상근 (YESIAM) 
        데이터 컬렉션 정보 출력 콘솔에 출력  
    */    
    urzzFunction.fnDataListViewConsole = function(taskId , searchVal){
        // 첫번? 파라메터어데  urzObjInfo.getBizInfo("FND00Map") 을 입력후 사용하세용
        // 두번? 파라메터 검색어.  값과 컬럼명 컬럼이름 모두 검색합니다.
        var EV_IPCMap = urzObjInfo.getBizInfo(taskId);
        var columnInfo = EV_IPCMap.columnInfo == undefined ? {} :EV_IPCMap.columnInfo;
        var param1Length = 0;
        var param2Length = 0;
        var showData = [];
        var lastView = EV_IPCMap["lastInsView"];
        lastView =typeof lastView != 'object'  ? {} : lastView ; 
        Object.keys(EV_IPCMap).forEach(function(eachKey){
         if(typeof EV_IPCMap[eachKey] != "function"){
             var name = columnInfo[eachKey];
             name = name == undefined ? "" : "(" +name+")";
             showData.push(eachKey +"@1 :" +  (lastView[eachKey] == undefined  ||  ["string", "number"].indexOf(typeof lastView[eachKey])  ? "" : " [" +lastView[eachKey]+"]")+ " @2 " +name  + "  " ) ;
             param1Length = param1Length < eachKey.length ?  eachKey.length :param1Length;
             param2Length = param2Length < name.length ?  name.length :param2Length;
         }
        }); 
        var srchVal = searchVal == undefined || searchVal == null ?  "" :searchVal; 
        showData.forEach(function(each){  
            var key = each.split("@1")[0];
            var rp1 = " ".repeat(param1Length - key.length);
            var rp2 = Object.keys(columnInfo).length == 0 ? "" : " ".repeat(param2Length - each.split("@1")[1].split("@2")[0].length); 
            var resultmsg = each.replace("@1", rp1).replace("@2", EV_IPCMap[key] +rp2 ); 
            if(resultmsg.toLowerCase().indexOf(srchVal.toLowerCase()) != -1)console.log(resultmsg);                  
        });
    }
    urzzFunction.Cs = function(searchVal){
        urzzFunction.fnDataListViewConsole(urzzFunction.mainObj.taskId , searchVal); 
    }
    /**
        2023-05-19
        이상근 (YESIAM) 
         공통 데이터셋에 함수셋팅과 마지막에 항목이 마지막에 입력받은 화면 입력 
    */    
    urzzFunction.fnLastInstView = function(params, taskId, pageId){
        var lastInsView  =  urzObjInfo.getBizInfo(taskId, "lastInsView");  
        lastInsView = lastInsView== undefined || lastInsView == "" ? {} : lastInsView;
            Object.keys(params).forEach(function(eachKey){ 
                lastInsView[eachKey] =  pageId;
            });
            urzObjInfo.setBizInfo(taskId, "lastInsView", lastInsView); 
    }
    
    /**
        2023-05-19
        이상근 (YESIAM) 
         다음으로 화면으로 넘어갈시 파라메터와 입력되어진 화면정보를입릭합니다
            (JSON : 입력할 데이터
            ,Object :urzObjInfo
            ,string : 현재페이지 아이디
            ,string : 작업아이디
            ,json : 한글항목입력)
    */    
    urzzFunction.fnSetBizDataMerge = function(insJson, pageId, taskId , ckrInfo){
         
        var lastInsView  =  urzObjInfo.getBizInfo(taskId, "lastInsView");  
        lastInsView = lastInsView== undefined || lastInsView == "" ? {} : lastInsView;
        // 데이터를 세팅합니다.
        Object.keys(insJson).forEach(function(key){
            if(!!insJson[key]  ||  insJson[key]  == 0){
                urzObjInfo.setBizInfo(taskId, key, insJson[key]);
                lastInsView[key] = pageId;
            } 
        });     
        
        if(ckrInfo != undefined) {
            var colInfoUpdate =urzObjInfo.getBizInfo(taskId , "columnInfo");
            colInfoUpdate = colInfoUpdate == "" ? [] : colInfoUpdate;
            urzzFunction.fnAssign(colInfoUpdate, ckrInfo);
            urzObjInfo.setBizInfo(taskId , "columnInfo", colInfoUpdate);
        };   
    } 
    /**
        2023-06-26
        이상근 (YESIAM) 
        assign 하는데 1번 json 에 해당하는 키의 값이 없을때 입력합니다.
     */
    urzzFunction.fnAssign = function(obja, objb){
        var returnValue = obja; 
        Object.keys(objb).forEach(function(key){
            if(objb[key] != "" || returnValue[key] == undefined){
                returnValue[key] = objb[key];
            }
        }); 
        return returnValue; 
    } 

    
    /**
        2023-05-19
        이상근 (YESIAM)  
        1번 파라메터 json 에 매핑할 정보를 지정하여 2번 vo기준 3번json파일로부터 파라메터를 설정합니다.  
        parameter (mappedJson : json , vo : EV_IPCMap , fromVo : json)
     */
    urzzFunction.fnParameterSetterFromInfo = function(mappedJson, vo, fromVo ){
        // vo정보와 매핑정보를 받아서 vo에 설정 합니다.
        Object.keys(mappedJson).forEach(function(eachKey){
            vo.set(eachKey, fromVo[mappedJson[eachKey]]);
        });
    }
    /**
        2023-06-01
        이상근 (YESIAM)  
        만나이를 호출합니다. 공통에 있는 로직이 작동을 하지않아서 설정
     */
    urzzFunction.fnGetAgeFromCsno = function(csno){
        csno = urzzFunction.fnBirthDate(csno);
        var nowDate  = new Date();        
        var csnoDate = new Date( csno.substr(0,4) 
                               , (csno.substr(4,2)*1)-1
                               , csno.substr(6,2));
        // 월일을 각각 가져와서 생일이 지났는지 확인합니다. 생일이 안지났을경우 년도-1을 합니다.
        var nowMnthDay  = (nowDate.getMonth()  +""+ nowDate.getDate()) *1 ;
        var csnoMnthDay = (csnoDate.getMonth() +""+ csnoDate.getDate())*1 ;
        // 만나이 생성
        var age = nowDate.getFullYear()-csnoDate.getFullYear() + (csnoMnthDay <nowMnthDay ? 0 :-1 );
        return age;
    }
    
    urzzFunction.fnBirthDate = function(csno){
        var csnoAt6 =csno.charAt(6); 
        var headYrInfo = {"20" : ['3','4','7','8']
                         ,"18" : ['0','9']}
        var headYr = "19";
        Object.keys(headYrInfo).forEach(function(key){
            if(headYrInfo[key].indexOf(csnoAt6)!=-1) {headYr = key};
        });
        
        return headYr+csno.substring(0,6);
        
    }
    
    // 날자, 포멧을 입력하면 알맞게 리턴
    urzzFunction.fnDateSetter = function(format , dateNum){
        if(!format || !dateNum){
            return "";
        }
        var dateNumArr =  dateNum.match(/[0-9]/g);
        return format.replace(/[a-z]/g, function(){return dateNumArr.shift()});
    } 
    /**
        2023-05-16
        이상근 (YESIAM)  
        포멧, 날자데이터를 입력하면 포멧에 맞게 데이터를 리턴합니다.
        예 :  input   -> format : "yyyy-mm-dd hh:mi:ss" datetime : "20230530231233"
              return -> '2023-05-30 23:12:33'
     */
    urzzFunction.fnFormatToDateTime = function(format, datetime){
        var tempDefault = format;
        var dateLng = 0;
        tempDefault.replace(/\s|:/gi,"-").split("-").forEach(function(dtKey){
            tempDefault = tempDefault.replace(dtKey , (datetime+"").substr(dateLng, dtKey.length))
            dateLng += dtKey.length;
        });  
        return tempDefault;
    }
    
    /**
        2023-06-09
        이상근 (YESIAM)  
        카멜형식을 스네이크로        
     */
    urzzFunction.fnCamelToSnake = function(value){
        return value.replace(/([A-Z])/g, function(str){
            return "_"+ str.toLowerCase();
        }).toUpperCase();    
    } 

    /**
        2023-06-09
        이상근 (YESIAM)  
        스네이크를 카멜로
     */
    urzzFunction.fnSnakeToCamel = function(value){
        return value.toLowerCase().replace(/(\_[a-z])/g, function(str){
            return str.toUpperCase().replace("_","");
        });    
    };
    
    /** 
     * 2023-06-15
     * 화면조작 파라메터 입력
     * 테스트용으로 화면의 객체를 입력하여 사용합니다.
     */
    urzzFunction.setmainObj  = function(mainObjObj, isTest){    
        urzzFunction.mainObj = mainObjObj;
        urzzFunction.isTest = isTest;
    }
    
    /**
        2023-06-15
        이상근 (YESIAM)  
        공통코드 라디오 박스로 설정 urzzFunction.setRdCodes
            공통코드기준으로 select1 객체에 구현된 라디오박스에 항목을 설정합니다.
            함수명  : urzzFunction.setRdCodes(object : json List || json ) 
            설명    : JSon 에 항목을 정의 한 후 다건일 경우 Json List, 단건일 경우 JSon데이터를 파라메터에 입력합니다.
            param  : { rdObj   : select1 의 아이디를 입력 
                     , code    : 공통코드 항목을 입력합니다
                     , orderby : 코드기준으로 정렬합니다. 아무것도 입력하지 않으면 asc 입니다.
                     , selected: 초기값으로 선택되어질 코드를 입력합니다.
                     }   
                      
            viewXML: <xf:select1 id="amlTrnsPdtCd" appearance="full" class="radio-btn small col3" cols="" ref="" renderType="radiogroup">
                        <xf:choices></xf:choices>
                     </xf:select1>
                  
        예1) 다건을 설정시
            var rdCombos =[{rdObj : riskCdInfo, code : "RISK_CD_INFO", orderby : "desc", selected : "1233" }
                          ,{rdObj : amlCd,     code : "AML_CD"    , orderby : "asc" , selected : "3312" } ];
            urzzFunction.setRdCodes(rdCombos);
        예2) 단건 설정시
            var rdCombo ={rdObj : amlCd,     code : "AML_CD"    , orderby : "asc" , selected : "3312" } ; 
            urzzFunction.setRdCodes(rdCombo);
    */  
    urzzFunction.setRdCodes = function(objParam, noCode, noUseArr){  
        // mainObj객체를 불러오기위해 입력되어진 첫번째 버튼 오브젝트로 부터 mainObj 객체를 가져옵니다.
        if(Object.keys(objParam).length <=0){return;}
        // jsonData가 입력되어졌을경우 배열에 넣어줍니다
        var objects = objParam[0] == undefined ? [objParam] : objParam;
        // 입력받은 객체에 데이터있는가 확인. json List<json> 두가지를 기문에 Object.keys 를 이용하여 길이확인
        var mainObjObj = objects[0].rdObj.getScopeWindow().mainObj

        urzObjInfo.showLoading("거래 처리 중 입니다.");
        new Promise(function(resolve, reject){ 
            
            // Invo DataSet  urzzFunction.getDatasetInfo 함수를 호출하여 가져옵니다.
            var inVo = urzzFunction.getDatasetInfo(mainObjObj);
             // 조회파라메터를 설정합니다. 공통코드 컬럼명:0을 콤마로 구분합니다.
            var searchData = "";
            objects.forEach(function(eachOjb){            
                searchData +=eachOjb.code+":0,";
            }); 
            //inVo에 설정
            inVo.set("cdDomnEnm",searchData.substring(0, searchData.length-1));
            inVo.set("cdClsfTypeNo","0");        
            // 공통코드를 가져옵니다.
            //공통코드 실행
            mainObjObj.$w.executeSubmission(urzzFunction.options.id);
            resolve();
        }).then(function(){ 
            // 결과값json 데이터를 가져옵니다.
            var rsJsonObj = mainObjObj.$w.getComponentById(urzzFunction.cdOptions[1].id).getAllJSON();
            // 코드그룹을 컬럼명 별로 저장합니다.
            if(rsJsonObj.length <= 0 ){
                urzObjInfo.alert("검색되어진 항목이 없습니다. \n" + searchData.replaceAll(":0","").replaceAll(",","\n") ); 
            }else{
                var codeJsInfo = {}; 
                var noInfo = !noUseArr ? [] : noUseArr;
                rsJsonObj.forEach(function(eachObj){
                    var keyCode = eachObj.cdDomnEnm.split(":")[0];
                    if(noInfo.indexOf(eachObj.code) == -1){
                        var tempCdInfo = Object.keys(codeJsInfo).indexOf(keyCode) != -1 ? codeJsInfo[keyCode] : {};
                        tempCdInfo[eachObj.code] = eachObj.name ;
                        codeJsInfo[keyCode] = tempCdInfo;
                    }
                }); 
                //출력되어진 데이터기준 라디오 버튼을 설정합니다.
                objects.forEach(function(eachObj){
                    if(codeJsInfo[eachObj.code].length <=0){
                        console.log("검색되어진 항목이 없습니다 :: " + eachObj.code )
                    }else if(!noCode){
                        // 라디오버튼을 생성후 첫번째 항목에 포커스를 줍니다.
                        urzzFunction.fnRdBtnSetter(codeJsInfo[eachObj.code] ,eachObj.rdObj ,eachObj.orderby =="desc" ? false: true ,eachObj.selected);
                    } 
                });
            }  
            urzObjInfo.hideLoading();
        });     
    };
    /**
        2023-06-15
        이상근 (YESIAM)
        공통코드 생성관련 라디오버튼 
    */     
    urzzFunction.getDatasetInfo = function(mainObjObj){
        var inVo= mainObjObj.$w.getComponentById(urzzFunction.cdOptions[0].id);
        // inVo가 생성 되어지지 않았을경우
        if(inVo == undefined){            
            //공통코드 invo outVo를 생성합니다.
            urzzFunction.cdOptions.forEach(function(eachObj){
                mainObjObj.$w.EV_IPC.create(eachObj); 
            });
            // 서브미션을 생성합니다.
            mainObjObj.$w.createSubmission(urzzFunction.options);
            // 생성되어진 invo를 재설정합니다.
            inVo = mainObjObj.$w.getComponentById(urzzFunction.cdOptions[0].id);
        }; 
        mainObjObj.$w.getComponentById(urzzFunction.cdOptions[1].id).removeAll();
        return inVo; 
    }
    /**
        2023-06-15
        이상근 (YESIAM)
        공통코드 상세항목을 화면에 표시    
            함수명 : urzzFunction.fnShowCodeInfo
            설명  : 테스트환경에서 공통코드 항목을 확인 할 수있습니다. 
            param : string 컬럼명 
        
        구현) mainObj.onpageload 함수에 다음과 같이 구현합니다.
            // 테스트 여부확인
            var isTest = true;
            //mainObj객체와 테스트여부를 입력합니다 
            urzzFunction.setmainObj(mainObj ,isTest); 
        예) 콘솔에서 취소정정 사유코드(canCorrRsnDvcd)를 조회합니다.
            console > urzzFunction.fnShowCodeInfo("canCorrRsnDvcd");
        
    */
    urzzFunction.fnShowCodeInfo =  function(colNm){
        if(urzzFunction.mainObj == null && urzzFunction.isTest) {
            alert("it can not use");
            return;
        }   

        urzObjInfo.showLoading("거래 처리 중 입니다.");
        // 공통코드를 가져옵니다.
        new Promise(function(resolve, reject){ 
            // 코드호출 비동기 처리
            setTimeout(function(){
                var inVo = urzzFunction.getDatasetInfo(urzzFunction.mainObj);
                var param = colNm.indexOf("_") == -1 ?  urzzFunction.fnCamelToSnake(colNm) :  colNm.toUpperCase();
                //inVo에 설정
                inVo.set("cdDomnEnm",param+":0");
                inVo.set("cdClsfTypeNo","0");
                //공통코드 실행
                urzzFunction.mainObj.$w.executeSubmission(urzzFunction.options.id);
                resolve();                
            }, 1000)
        }).then(function(){
            // 결과값json 데이터를 가져옵니다.
            var rsJsonObj = urzzFunction.mainObj.$w.getComponentById(urzzFunction.cdOptions[1].id).getAllJSON();
            if(rsJsonObj.length <= 0 ){
                urzObjInfo.alert(colNm + " 에대한 정보가 없습니다");
            }else{
                var EV_IPCs = [];
                rsJsonObj.forEach(function(eachObj){
                    EV_IPCs.push({"Kind" : eachObj.code , "Content" : eachObj.name});
                });
                var jsonMap = {"title":colNm, "oktext":"OK", "canceltext":"CANCEL", "nick":"C", "gmsg":"가이드메세지"};
                urzObjInfo.gridAlertTo(jsonMap, EV_IPCs); 
            } 
            urzObjInfo.hideLoading();
        });
    }
    
    urzzFunction.Cd = function(colNm){
        urzzFunction.fnShowCodeInfo(colNm); 
    }
    
    urzzFunction.Cp = function(){
        urzObjInfo.hideLoading()        
    }
     
    /**
        2023-06-21
        이상근 (YESIAM)
        입력되어진 소수를 자리별로 잘라줍니다.
    */
    urzzFunction.fnFloorFloat = function(intVal, under){
        var unserVal = ("1" +  "0".repeat(under))*1;
        return Math.floor((intVal)* (unserVal))/unserVal
    }
    /**
         2023-06-21
        이상근 (YESIAM)
        비중별 입력되어진 금액은 소수점 2자리만 사용할수 있도록 하기위해 따로 재정의 하였습니다.
     */
    urzzFunction.fnFloorMpVal = function(intVal){
        return urzzFunction.fnFloorFloat(intVal,2);
    } 
    
    /**
        2023-06-21
        이상근 (YESIAM)
        비중별로 입력되어진 금액을 분배합니다.
        
        param  : {EV_IPCArr      : JSON 리스트 
                 ,valPrice     : (Number) 분배할 금액을설정
                 ,perValClum   : (String) EV_IPCArr에 비중이 설정되어진 컬럼
                 ,priceValClum : (String) EV_IPCArr에 valPrice를 분바할 컬럼
                 }   
       예)  // dtlLstApdPdtItmz 데이터리스트의 항목을 가져옵니다.
           var EV_IPCArr = dtlLstApdPdtItmz.getAllJSON();
           // dtlLstApdPdtItmz 데이터 리스트의 비중표현하는 컬럼은 mpImpt, 값은  depsAmt 입니다.
           var workedJsonList= urzzFunction.fnSetMpInfo(EV_IPCArr,300000,"mpImpt","depsAmt" );
           // 비중별로 금액을 설정한 데이터를 화면에 설정합니다.
           dtlLstApdPdtItmz.setJSON(EV_IPCArr); 
    */
    urzzFunction.fnSetMpInfo = function(EV_IPCArr, valPrice ,perValClum, priceValClum){
        // 배분되어진 비중을 합하여 저장합니다.
        var allPersent = 0; 
        // 100을 넘을경우를 방지하기위해서 순차적으로 비중을 임시저장합니다.
        var ablePers = 0;
        // 데이터가 없는 카운트를 추가합니다. 집계되어진 비중을 항목별로 나누어 배분합니다
        var noDataCnt = 0;
        // 최종적으로 비중이 없는 항목을 저장합니다.
        var lstNodtIdx = -1;
        // 기본데이터를 집계합니다.
        EV_IPCArr.forEach(function(each , idx){
            // 로우의 금액이 0일경우 마지막 항목으로 지정합니다.
            lstNodtIdx = (each[perValClum]*1) == 0 ? idx :lstNodtIdx;
            // 모든비중을 저장합니다.
            allPersent += each[perValClum] *1;
            // 모든비중이 100이상일경우
            if(allPersent >=100){
                // 100을 넘겼을경우 해당로우의 비중을 0으로 변경후 재분배 할수있도록 합니다.
                EV_IPCArr[idx][perValClum] = 0;
            }else{
                // 가능범위의 비중을 저장합니다.
                ablePers+= each[perValClum] *1;                
            }
            // 현재 비중이 0일경우 분배를 위해서 총 개수를 지정합니다.
            noDataCnt += EV_IPCArr[idx][perValClum] == 0 ? 1 :0;
        });
        // 현재 분배할 비중을 산출합니다.
        var nowP =  urzzFunction.fnFloorMpVal(100 - ablePers);
        // 각각 분배할 비중을 산출합니다.
        var eachPs =  urzzFunction.fnFloorMpVal(nowP / noDataCnt);
        // 마지막에 들어갈 비중을 지정합니다. 100%를 맞추기 위해 현재 각각분배할 비중의 합에서 분배할비중을 뺀 비중을 마지막 컬럼에 입력합니다.
        var lastAd =  urzzFunction.fnFloorMpVal(nowP - urzzFunction.fnFloorMpVal(eachPs*(noDataCnt == 0 ?nowP : noDataCnt )));
        // 비중별 금액을 분배합니다.
        EV_IPCArr.forEach(function(each, idx){
            // 현재로우에 입력되어진 비중을 가져옵니다.
            var nowPrsnt = EV_IPCArr[idx][perValClum]*1;
            // 현재 비중이 0일경우 각각분배 비중을 입력하고 아닐경우 해당로우의 비중을 입력합니다.
            // 만약에 마지막로우일경우 산출된 비중+마지막입력비중을 합게 입력합니다. 
            nowPrsnt = nowPrsnt==0 ? eachPs 
                                   : lstNodtIdx==-1 && idx == (EV_IPCArr.length-1)? urzzFunction.fnFloorMpVal(nowPrsnt +nowP) : nowPrsnt;
            // 비중은 소숫점 두자리까지 표시합니다.
            EV_IPCArr[idx][perValClum] = (nowPrsnt).toFixed(2);
            // 비중별로 값을 분배합니다.
            EV_IPCArr[idx][priceValClum] = Math.round(valPrice*(nowPrsnt/100));
        }); 
 
        return EV_IPCArr; 
    }
    /**
        2023-06-22
        이상근 (YESIAM)
        urzzFunction.fnSetMpInfo 를 재정의 합니다.jsonArr 파라메터에 DataList 지정하여 데이터가 바로 설정되도록 합니다.
        수정된 데이터는 리턴 할수 있습니다.
    */
    urzzFunction.fnSetMpDataList = function(EV_IPCList, valPrice ,perValClum, priceValClum){
        var EV_IPCRs = urzzFunction.fnSetMpInfo(EV_IPCList.getAllJSON(), valPrice ,perValClum, priceValClum);
        EV_IPCList.setJSON(EV_IPCRs);
        return EV_IPCRs;
    }
    
    /**
        2023-06-22
        이상근 (YESIAM)
        테스트 항목에 말풍선을 입력
    */
    urzzFunction.fnCompMsgForTest = function(comp, msg){  
        var compInfo = document.getElementById(comp.id);
        var compPos = compInfo.getBoundingClientRect()
        var temp =   "<span id='"+comp.id+"msg' onclick = 'urzzFunction.aaaa(this)' style='width:400px;"
                    + "background:#8e1717;"
                    + "             color:#fff;"
                    + "                height:150px;                         "
                    + "             font-weight:bold;                    "
                    + "             padding:5px;                         "
                    + "             font-size:30px;                      "
                    + "             position:absolute;                   "
                    + "             border-radius:20px;                  "
                    + "             z-index:2;                           "
                    + "             top: 10px ;"
                    + "             left:100px  ;"
                    + "             border-top:16px solid transparent;   "
                    + "             border-right:6px solid  #681616;     "
                    + "             border-bottom:6px solid transparent; "
                    + "             border-left:16px solid transparent;  "
                    + "    '> @msg </span>".replace("@msg", msg) 
        $("#"+comp.id).before(temp)
    }
    
    urzzFunction.fnCompMsgHideInfo = function(objVal){
        $("#"+objVal.id).attr("style","visibility:hidden")
    }
    /**
        2023-07-01
        이상근 (YESIAM) 
        데이터맵 혹은 데이터리스트를 입력하면 항목과 항목명 : 항목명+아이디 JSON을 리턴합니다.
    */
    urzzFunction.fnColInfoFromDatalst = function(EV_IPClstObj){
        var resultInf = {};
        var isDataList = EV_IPClstObj.getObjectType() == "EV_IPCList" ;
        var jsonInfo = isDataList  ?  EV_IPClstObj.getAllJSON()[0] : EV_IPClstObj.getJSON();
        var nowId = EV_IPClstObj.org_id;
        Object.keys(jsonInfo).forEach(function(each){            
            resultInf[each] = (isDataList ? EV_IPClstObj.getColumnName(each) : EV_IPClstObj.getName(each)) + "_" + nowId;
        });
        return resultInf;
        
    }
     
    
    
    
    /*
     * 공통팝업 변수 집합
     * workspace WEB-INF\\views 아래 있는 xml파일에서 팝업항목을 모두 스캔하여 작성
     * 
     * 데이터 수집코드 :  /FindPopupAllFile.java 
     * */
    urzzFunction.popupInfo = {
              COM___63  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 900} , popkey  : { popkey : "COM00___key"} , title :  "**************서류" }  
            , COM___64  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 800} , popkey  : { popkey : "COM00___key"} , title :  "***조회" }  
            , COM___65  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 800} , popkey  : { popkey : "COM00___key"} , title :  "***검색" }  
            , COM___66  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 300} , popkey  : { popkey : "COM00___key"} , title :  "소비***" }  
            , COM___69  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 270} , popkey  : { popkey : "COM00___key"} , title :  "고객***입력" }  
            , COM___93  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "COM00___key"} , title :  "전자******조회" }  
            , COM___94  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 350} , popkey  : { popkey : "COM00___key"} , title :  "상품***************" }  
            , COM___00  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "COM00___key"} , title :  "*********" }  
            , COM___31  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 800} , popkey  : { popkey : "COM01___key"} , title :  "******" }  
            , CRD___15  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 500} , popkey  : { popkey : "CRD06___key"} , title :  "기************조회" }  
            , CRD___40  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CRD06___key"} , title :  "회원정보*********조회" }  
            , CRD___45  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CRD06___key"} , title :  "결제*********정보" }  
            , CRD___50  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 500} , popkey  : { popkey : "CRD06___key"} , title :  "카드*******검색" }  
            , CUS___04  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 470} , popkey  : { popkey : "CUS02___key"} , title :  "위임************" }  
            , CUS___05  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 470} , popkey  : { popkey : "CUS02___key"} , title :  "대리************" }  
            , CUS___07  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 470} , popkey  : { popkey : "CUS02___key"} , title :  "신분*********사유" }  
            , CUS___40  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS02___key"} , title :  "국가***조회" }  
            , CUS___41  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS02___key"} , title :  "직원******조회" }  
            , CUS___42  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS02___key"} , title :  "*********코드조회" }  
            , CUS___50  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS02___key"} , title :  "우편***조회" }  
            , CUS___06  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS04___key"} , title :  "카드상세정보조회" }  
            , CUS___10  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS04___key"} , title :  "과목코드조회" }  
            , CUS___15  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS04___key"} , title :  "고객상세정보조회" }  
            , CUS___20  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS04___key"} , title :  "고객검색" }  
            , CUS___31  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 350} , popkey  : { popkey : "CUS04___key"} , title :  "수수료거래별상세정보" }  
            , CUS___40  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS05___key"} , title :  "고객분류코드" }  
            , CUS___45  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS05___key"} , title :  "소득자구분" }  
            , CUS___50  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS05___key"} , title :  "거래자구분코드" }  
            , CUS___55  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS05___key"} , title :  "신BIS고객구분" }  
            , CUS___60  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS05___key"} , title :  "국가코드조회" }  
            , CUS05__5  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS05__5key"} , title :  "AML코드조회" }  
            , CUS05__0  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "CUS05__0key"} , title :  "표준산업분류코드조회" }  
            , CUS08__4  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 470} , popkey  : { popkey : "CUS08__4key"} , title :  "FATCA/CRS조작자확인사항" }  
            , DBI01__6  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "DBI01__6key"} , title :  "구속***스트" }  
            , DPS04__0  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 500} , popkey  : { popkey : "DPS04__0key"} , title :  "우대금리신청번호조회" }  
            , DPS05__8  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 500} , popkey  : { popkey : "DPS05__8key"} , title :  "우대금리신청번호조회" }  
            , DPS09__0  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 500} , popkey  : { popkey : "DPS09__0key"} , title :  "거래***세" }  
            , DPS09__5  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "DPS09__5key"} , title :  "고객별***조회" }  
            , DPS12__5  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 500} , popkey  : { popkey : "DPS12__5key"} , title :  "자기앞*********청" }  
            , DPS13__3  : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 300} , popkey  : { popkey : "DPS13__3key"} , title :  "해*********분선택" }   
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__12Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__13Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__14Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__15Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__16Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__17Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__18Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__19Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__20Key"}, title :  "** 목록조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__21Key"}, title :  "**카드번호 **조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__25Key"}, title :  "**코드 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__26Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__27Key"}, title :  "** 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM0__29Key"}, title :  "회원별 카드번호 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM02__0Key"}, title :  "고객검색" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM02__1Key"}, title :  "** 목록 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM02__2Key"}, title :  "** 코드조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM02__3Key"}, title :  "** 조회" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM02__1Key"}, title :  "** 등록" }  
            , PCOM020___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 700} , popkey  : { popkey : "PCOM02__5Key"}, title :  "**번호" }  
            , PCOM030___ : {pageUrl : "uri/***.xml", options : { width  : 1000 , height: 500} , popkey  : { popkey : "PCOM03__4Key"}, title :  "출금연동**입력" }   
        };           
      // 콜백  
     
    /**
     * 공통팝업호출 공통을 추가하였습니다
       버튼구현코드 : <w2:anchor outerDiv="false" style="" id="" class="btn-search"
                                 ev:onclick="urzzFunction.fnClickBtnPopup" popupName="" beforFnName="" callbackFnName=""
                              compCd="" compNm="" EV_IPCMap="" EV_IPCMapCd="" EV_IPCMapNm="">
                    <xf:label><![CDATA[조회]]></xf:label>
                </w2:anchor>
       popupName      : urzzFunction.popupInfo 의 키값입니다
       callbackFnName : 팝업이 종료되어진 이후에 행해지는 이벤트 입니다. 
                        화면에 함수를 구현후 함수명을 파라메터에 입력 하면 JSON파라메터를 리턴받습니다.
                        callbackFnName 이 정의 하지않을경우 디폴트로  compCd, compNm 항목의 정보가 있을경우 각각 입력이 되어지고
                        compCd 가없고 compNm이 있을경우 cd cdNm 순으로 compNm이 입력되어집니다.
                        EV_IPCMap 에 EV_IPCMap아이디를 입력후   EV_IPCMapCd , EV_IPCMapNm 에 입력되어진 코드정보에 각각 입력이되어집니다.
       beforFnName    : 팝업이 호출되기전에 파라메터를 설정합니다. 
       compCd         : 코드가 입력되어지는 컴포넌트를 지정합니다.
       compNm         : 명칭이 입력되어지는 컴포넌트를 지정합니다.
       EV_IPCMap        : DataMap을 지정하여 선택한 항목의 데이터를 저장합니다.
       EV_IPCMapCd      : DataMap에서 코드가 저장되어질 항목명을 지정합니다
       EV_IPCMapNm      : DataMap에서 명칭 저장되어질 항목명을 지정합니다
        
    </pre>
     */ 
    urzzFunction.popupmainObj = {};
    urzzFunction.popBtnColInfo = ["popupName","callbackFnName","beforFnName","compCd","compNm","EV_IPCMap","EV_IPCMapCd","EV_IPCMapNm"]
    /* 공통 호출함수 */
    urzzFunction.fnClickBtnPopup = function() {  
        // 클릭되어진 팝업 버튼의 정보를 가져옵니다. 
        // userData를 호출하여 알맞게 파라메터를 설정합니다. 
        urzzFunction.popupmainObj = {}; 
        for(var i = 0; i <  urzzFunction.popBtnColInfo.length; i++){
            var eachCol = urzzFunction.popBtnColInfo[i];
            urzzFunction.popupmainObj[eachCol] = this.getUserData(eachCol); 
        } 
        // 유져데이터 컴포넌트를 체크합니다.   
         var userDataCompChk = this.getUserData == undefined ;
         // mainObj 객체를 가져옵니다.
        var noivvainObj = this.getParent().getScopeWindow().mainObj;
        urzzFunction.popupmainObj.mainObjObj = noivvainObj;
         // 버튼에서 클릭하였을경우 아이디 값(org_id)을  호출하여 팝업 버튼 정보를 가져옵니다.
        var orgIdInfo = urzzFunction.popupmainObj["popupName"];
        var popInfo = urzzFunction.popupInfo[orgIdInfo]; 
        popInfo.org_id = orgIdInfo;
        // 항목정보가 없을경우 정지합니다.  
        // 버튼에서 클릭하였을경우 콜백함수명을 callbackFnName 에서 가져옵니다.
        // 콜백파라메터를 받지 않은경우는 default로 fnCallBackBtnPopup 명칭을 호출합니다
        popInfo.options.callback = urzzFunction.popupmainObj["callbackFnName"] == undefined?   "fnCallBackBtnPopup" : urzzFunction.popupmainObj["callbackFnName"];
        if(noivvainObj.fnCallBackBtnPopup == undefined) noivvainObj.fnCallBackBtnPopup = urzzFunction.fnCallBackBtnPopup;
        // 팝업출력 이전에 호출하는 함수를 지정합니다 
        var bfFnNm = urzzFunction.popupmainObj.beforFnName;  
        // 팝업출력이전함수는 팝업에 사용되어질 파라메터를 가져옵니다.
        var popParam = bfFnNm == undefined  ? {isShowPopup:true} : noivvainObj[urzzFunction.popupmainObj.beforFnName.split(".")[1]](urzzFunction.popupmainObj);
         
        if(popParam.isShowPopup){
            urzObjInfo.openPopup(noivvainObj, popInfo.title , popInfo.pageUrl, popInfo.options, popParam);
        }    
    };
 
    // 디폴트로 설정되어지는 콜백함수  urzObjInfo.CALLBACK_FUNCTION_NAME 에 지정된 명칭 그대로 갑니다.
    urzzFunction.fnCallBackBtnPopup = function(selCdInfo) {  
        if(selCdInfo == null) return;
        /*
         *  코드입력 컴포넌트가 없을때 명칭입력 콤포넌트에 코드 + 이름으로 입력합니다
         *  데이터셋이 설정되어졌을경우 데이터 셋에 코드를 설정합니다.  
         * */
        // 결과값의 코드와 명칭을 따로 저장합니다.
        var csVal = {cd : ""  , nm : "" } 
        // csVal 에 저장되어진 파라메터는 코드와 명칭의 뒷자리의 공통입니다. 각각 다른 파라메터를 리턴하여 Cd , Nm으로 지정합니다. 
        var isValid = false;
        var rsInfo = urzzFunction.popupmainObj;
        var mainObjObj = rsInfo.mainObjObj;
        Object.keys(csVal).forEach(function(witch){ 
            var colVal = Object.keys(selCdInfo).find(function(colNm){return colNm.toLowerCase().substr(colNm.length -2) == witch})
            csVal[witch] =  selCdInfo[colVal];
            isValid =  selCdInfo[colVal] == "" || selCdInfo[colVal] == undefined ? isValid :true;
            // 데이터맵에 설정 
            if(rsInfo.EV_IPCMap != undefined){ 
                var headUpper ="EV_IPCMap" + (witch.replace(/^[a-z]/, function(c){return c.toUpperCase()}));
                mainObjObj.$w.getComponentById(rsInfo.EV_IPCMap).set(rsInfo[headUpper] ,selCdInfo[colVal]);
            }
        });
        if(!isValid){
            alert("call back함수를 구현후 컴포넌트 프로퍼티 callbackFnName callback 함수명을 지정후 " + Object.keys(selCdInfo) +"항목의 리턴값을 사용하는 기능을 구현 하여주십시오 ");
            return;
        } 
        // 코드입력 컴포넌트가 없고 명칭이 있을경우 명칭컴포넌트에 코드 + 명칭을 입력합니다.
        if(rsInfo.compCd == undefined && rsInfo.compNm != undefined ){
            mainObjObj.$w.getComponentById(rsInfo.compNm).setValue(csVal.cd +" " + csVal.nm);
        }else if(rsInfo.compCd != undefined && rsInfo.compNm != undefined ){
            // 코드 컴포넌트와 명칭 컴포넌트가 두개다 있을경우 각각 설정합니다.
            var colInfo = {cd : "compCd"  , nm : "compNm" } 
            Object.keys(selCdInfo).forEach(function(each){
                var lastVal = each.toLowerCase().substring(each.length-2)
                var nowObjNm = urzzFunction.popupmainObj[colInfo[lastVal]]; 
                mainObjObj.$w.getComponentById(nowObjNm).setValue(selCdInfo[each]);
            });
        } 
        
    }
    
    /**
        2023-07-07
        이상근 (YESIAM)
        데이터맵 생성
     */
    urzzFunction.creDocFromDtl = function(id , EV_IPCList){
        var colInfo = []; 
        // 컬럼셋팅을 합니다.
        Object.keys(EV_IPCList.getJSON()).forEach(function(key){
            colInfo.push({id: urzzFunction.fnCamelToSnake(key) , name : key , EV_IPCType :"text", value :"" });
        });
        var mainObjObj = EV_IPCList.getScopeWindow().mainObj;
        mainObjObj.$w.EV_IPC.create({ "id"      : id
                                  , "type"    : "EV_IPCMap"
                                , "option"  : {"baseNode" :"map"}
                                , "keyInfo" : colInfo     }); 
        
    }
    
    /**
        2023-07-07
        이상근 (YESIAM)
        서식데이터를 호출하면서 데이터를 입력하
    */
    urzzFunction.getFxdfColInfoDefault = function(keyLst){
        var returnValue = {};
        var keyLstInfo = typeof(keyLst) == "string" ? [keyLst] : keyLst; 
        keyLstInfo.forEach(function(eachKey){
            urzzFunction.sersic[eachKey].forEach(function(eachJSON){
                returnValue[eachJSON.id] = "";
            });
        });        
        return returnValue;
    }
    
    /*
     *     서식데이터 
     * */
    // 서식데이터
    urzzFunction.sersic = {
     "**1188":[{"name":"집주인0","id":"OWNER0"}],
      "**1246":[{"name":"서비스신청여부","id":"SERVICE_1"}], 
     "**3135":[ ],
      "**2018":[ ],              
     "**3159":[ ],
     "**9054":[ ],
     "**9143":[ ],
     "**1382":[{name :'****', id : 'DATA_NAME'},
               {name :'*****', id : 'DATA_INFO'},{name :'계좌번호', id : 'ACCOUNT'} ]
     
     };
 
    
    
    /**
        2023-07-26
        이상근 (YESIAM)  
        팝업메세지 타이틀, 본문 형태로 표시
    */
    urzzFunction.openMessagePopup = function(objmainObj, valJson){ 
        // 팝업아이디 설정
        var id = "Alert_" + (+new Date) +objmainObj.taskId +objmainObj.pageId ;
        return new Promise(function(resolve, reject){
            // urzObjInfo.SHOWUPINFO 에 resolve함수를 입력후 확인후 처리 재정의
            urzObjInfo.SHOWUPINFO[id] = resolve;
            var rs = urzObjInfo.openPopup(objmainObj, "Message", "/common/uzr/popup.xml"
                                    , {popID:id , width:425, height:175, modal:true, resizable:false, popClass:"alert", disableCloseButton:true, noHistory:"Y"}
                                    , valJson );
            urzzFunction.Cp();
        });
    }
  
    /*******************************************전송문자 공통*************************************/       
    // 전송문자 파라메터를 지정합니다.    
    urzzFunction.fxdSetDataFnJSON = {
    }
    // 전문전송데이터
    urzzFunction.dataUtil = [];
    //         
    /**
        2023-08-05
        이상근 (YESIAM)
        데이터 서식생성 ,EV_IPCList 생성
    */    
    urzzFunction.fnMakeFxdMapperDataList = function(fxdfLst,mainObjObj){
        var colInfo = []; 
        // 출력할 서식항목(mainObj.expo***.*****) 으로 공통에 저장되어진 컬럼 정보를 호출하여 데이터 셋을 생성합니다
        fxdfLst.forEach(function(each){
         // urzzFunction.sersic 에 저장되어진 전송문자 컬럼 항목을 호출합니다.
            var nowInfo = urzzFunction.sersic[each];
            // 생성할 데이터셋의 정보를 저장합니다.
            nowInfo.forEach(function(objNowInfo){
             // 이미 있는정보인지 확인합니다
                var isInsert = colInfo.find(function(jsonObj){return jsonObj.id==objNowInfo.id}) == undefined;
                // 없으면 데이터타입 "text" 를지정한다음 입력합니다.
                if(isInsert){ 
                 objNowInfo.EV_IPCType ="text";
                    colInfo.push(objNowInfo);
                }
            });
        });
        
        // docParam 을 생성합니다.
        mainObjObj.$w.EV_IPC.create({ "id"      : "docParam"
                                      , "type"    : "EV_IPCMap"
                                    , "option"  : {"baseNode" :"map"}
                                    , "keyInfo" : colInfo     }); 

        mainObjObj.$w.getComponentById("docParam").setJSON({});
     
        return colInfo;
    } 
    
    
    /**
        2023-08-05
        이상근 (YESIAM)
        8***   설정 초기화를 합니다. 
    */     
    urzzFunction.initParamter = function(paramData ,fxdLst){ 

        // 전문전송데이터
        urzzFunction.dataUtil = [ 
                  {pname :"date"             , utilNm : ["DATE","DDAT"]}
                , {pname :"val1"             , utilNm : []   , "fnGetData" : function(param){return "1"}}
                , {pname :"val2"             , utilNm : []   , "fnGetData" : function(param){return "2"}}
            ]; 
        /* 현재 설정되어진 전송문자 항목을 지정합니다.*/
         urzzFunction.fxdSetDataFnJSON   = {
                     "**2017" : urzzFunction.fnFxdf__2017
                   , "**2094" : urzzFunction.fnFxdf__2094
                   , "**2095" : urzzFunction.fnFxdf__2095
                   , "**2102" : urzzFunction.fnFxdf__2102
                   , "**1421" : urzzFunction.fnFxdf__1421
                   , "**2084" : urzzFunction.fnFxdf__2084
                   , "**3135" : urzzFunction.fnFxdf__3135
                   , "**9143" : urzzFunction.fnFxdf__9143
                   , "**1188" : urzzFunction.fnFxdf__1188
                   , "**3159" : urzzFunction.fnFxdf__3159 
                   , "**1382" : urzzFunction.fnFxdf__1382
                   , "**1246" : urzzFunction.fnFxdf__1246
                   , "**2018" : urzzFunction.fnFxdf__2018
                 } 
        // 매칭할 파라메터 설정
        var param =  Object.assign(paramData, urzObjInfo.getBizCustInfo()); 
        Object.assign(param, urzObjInfo.getUserInfo())
         // YY, MM, DD 에 쓸 포멧을 설정합니다.
        var dateForMat = "yyMMdd";
        // 위 포멧기준 날짜 날짜호출
         
        // 고객정보와 bizInfoe데이터를 호출하여 매칭되어지는 데이터를 입력합니다
         var jsList = [urzObjInfo.getBizCusComInfo(), urzObjInfo.getBizCustInfo()];
         jsList.forEach(function(jsListEach){
             // 표시할 컬럼 정보와 비교하여 데이터를 가져와서 ColSet에 입력합니다. 데이터가 없는 데이터는 제외합니다.
             var insetKey = Object.keys(jsListEach).filter(function(key){return Object.keys(param).indexOf(key) == -1});
             console.log(insetKey)
             // docParam에 데이터를 입력합니다.                                                            
             insetKey.forEach(function(eachkey){ 
                 param[eachkey] =jsListEach[eachkey];
             });
         });
          
        var dateTemp = WebSquare.date.getCurrentServerDate(dateForMat).match(/\d{2}/g);
        // YY, MM,DD 항목을 저장합니다. 
        dateForMat.toUpperCase().match(/[A-Z]{2}/g).forEach(function(each,idx){ 
            param[each.toLowerCase()] = dateTemp[idx] 
         });   
         
         // 서식에 입력할 파라메터를 설정합니다
         // fun 이 있을경우 함수를 실행하여 데이터를 가져올수 있도록 합니다.
         // utilNm에서도 적용할 파라메터를 함수로 지정할수 있습니다. 
         param.telNo = param.ddd + "-" + param.tlxcno + "-" + param.tlndno;
        param.mpNo =  param.mpidno + "-" + param.mpexno + "-" + param.mpndno;
        param.offiTelNo = param.offiDdd + "-" + param.offiTlxcno + "-" + param.offiTlndno; 
         
         // 함수항목을 가져옵니다.
         var returnLst = urzzFunction.getFxdfColInfoDefault(fxdLst); 
         // 데이터 확인용 제이슨
        var exposerFxdfJs = {};
        // 데이터를 설정할 정보를 초기화 합니다.
        var dataUtil = urzzFunction.dataUtil;
        // urzzFunction.fxdSetDataFnJSON  JSON 에 저장되어있는 서식번호별
        // 데이터 설정 함수를 실행하여 함수를 서식별로 설정합니니다.
        fxdLst.filter(function(findNo){return Object.keys(urzzFunction.fxdSetDataFnJSON).indexOf(findNo) != -1})
              .forEach(function(eachNo){ 
                  // 서식별 데이터를 실행후  param, dataUtil 을 반환합니다.
                  var setTemp = urzzFunction.fxdSetDataFnJSON[eachNo](param ,dataUtil)
                  param = setTemp.param;
                  dataUtil = setTemp.dataUtil;
              }
        );
        
        // 서식에 입력할 데이터를 설정합니다
         fxdLst.forEach(function(each){
             //반영할 데이터리스트를 가져옵니다.
             var tempData = urzzFunction.sersic[each];
             tempData.map(function(eachCol){  
                 // utilNm 이 함수일경우에는 실행한 리턴값을, 아닐경우 배열값을 가져옵니다.
                 var pnmInfo = dataUtil.find(function(obj){return (typeof(obj.utilNm) == "function" ? obj.utilNm() : obj.utilNm).indexOf(eachCol.id) != -1});
                 // 반영할 항목의 데이터가 있을경우
                 if(!!pnmInfo){ 
                     // 확인용 변수, 적용할 컬럼에 데이터 입력 
                     eachCol.value = !pnmInfo.fnGetData ? param[pnmInfo.pname] : pnmInfo.fnGetData(param);
                     returnLst[eachCol.id] = eachCol.value;
                 } 
                 return eachCol;
             }); 
             //데이터 확인용
             exposerFxdfJs[each] = tempData;
         });

         return returnLst;
    } 
    /**
        2023-08-31
        이상근 (YESIAM) 
        *** 대한 강화된 설명의무 이행 확인서 [****46] 의 항목입력  
    */     
    urzzFunction.fnFxdf__1246 = function(param , dataUtilLst){
        var colInfoCommon = {
             "HGHAGE_FLR_APIR_LEME_SV_APL_YN" : "val1"
            ,"AGN_HW_NM"                      : "hepPrsnNm"
            ,"AGN_HW_SIGN"                    : "hepPrsnSign"  
        }
        var guestAgAtgr = "WHO_AG_ATGR_MENT_"
        var guestVdAtgr = "WHO_WEA_ATGR_MENT_"
        for(var i = 1 ; i <= 7; i++){
            colInfoCommon[guestAgAtgr + i] =  guestAgAtgr + i;
            if(i <=2){ 
                colInfoCommon[guestVdAtgr + i] =  guestVdAtgr + i; 
            }
        }

         return urzzFunction.fnFxdfRtnSetter(param,dataUtilLst, colInfoCommon);
    }
    /**
        2023-08-05
        이상근 (YESIAM) 
        위험가중자산 산출전 부도욜 확인서 [**2084] 의 항목입력  
    */     
    urzzFunction.fnFxdf__2084 = function(param , dataUtilLst){
         var colInfoCommon = { 
            // "POS_FNM_NM_1"     : "mmaPsitCdnm"  판매직원성명 암호화데이터가 아니어서 오류 발생   
             "UDSD_YN"                  : "udsdYn1"
            ,"CNSL_POS_PSIT"            : "mmaPsitCdnm"  
            ,"MNGM_RPPR_STBT_OPNN_YN"   : "val1"
            ,"NVST_EPL_MENT_1"          : "signedImg1"
            ,"NVST_EPL_MENT_2"          : "signedImg2"  
            ,"OOVF_SERVICE_TYPE"        : "val2"
           
        }; 
        // 중복되어지는 항목을 지정합니다.
        var colInfoAddNum = {
             "SHPNM"       : "deptName"
            ,"POS_PSINM"   : "mmaPsitCdnm" 
            ,"POS_SIGN"    : "mmaSign" 
            ,"POS_FNM_NM"  : "mmaNm"
            ,"VAL_NM"      : "guestHwNm"
            ,"VAL_SIGN"    : "guestHwSign" 
        }
        // 중복되어지는 항목의 값을 1,2,3 으로 지정합니다.
        for(var i = 1 ; i <=2 ; i++){
            Object.keys(colInfoAddNum).forEach(function(key){
                colInfoCommon[key + "_" + i] = colInfoAddNum[key];
            })
        }
        // 여러문건일경우 사용할 컬럼들 입니다.
        var colInfo = { "WHO_EXPOSER_TTLE"      : "pdtNmFGd"
                      , "SELL_COMP_EXPOSER_TTLE" : "pdtNm"
                      , "UND_GD_NM"      : "exposerRiskGdcNm"
                      , "UND_GD"         : "exposerRiskGdc" 
                      };
        
        //  유의사항확인
        param.udsdYn1 = "1";
        // 고객이해여부
        param.guestUdsdYn = "1"
        if(["20","30"].indexOf(param.ivvSrvyPurpDvcd) !=-1){
            var exposers = param.t0;
            /*
            //  @@길이가 길경우 14자리로 자르고 .. 을 붙입니다.
            exposers.map(function(obj){
                obj.pdtF = obj.pdtNm.length > 28 ? obj.pdtNm.substring(0,28)+".." : obj.pdtNm; 
                return obj;
            });
            */ 
            exposers.forEach(function(each, idx){
                Object.keys(colInfo).forEach(function(key){
                    var paraName = colInfo[key];
                    var tail = "_"+(idx +1);
                    colInfoCommon[key+tail] = paraName +(idx +1) ;
                    // 파라메터명이 pdtNmF 일경우 투자신청계좌명에 위험등급을 붙여서 저장합니다. 
                    var nowValue = paraName == "pdtNmFGd"? each["pdtNm"]+"(@gd)".replace("@gd",each["exposerRiskGdcNm"] ) : each[paraName];  
                    // 파라메터값을 설정합니다.
                    param[paraName +(idx +1)] = nowValue
                 
                });
            });
        }  
        colInfoCommon.UND_GD_NM_5  = "val1"
        // 99 이상일경우 
        if(param.arage >= 99){  
 
            colInfoCommon.VAL_NM_3 = "nameOfgame" 
            colInfoCommon.VAL_SIGN_3 = "yesiam"
            param.val0 = "0";
            if(["04","08"].indexOf(param.outsReptVbaaClacd) != -1){
                for(var i = 1 ; i<=5; i++){
                    colInfoCommon["MNGM_RPPR_CHK_LST_YN_"+i] = "val1"
                }
            }
            
            // 참여자 여부가 정상일경우
            if(param.wwpovaccLemeSvYn == "1"){  
 
                colInfoCommon.DD_3 = "dd" 
                param.ovaccRelSignF =  param.w___elSign;
                param.ovaccRelNmF =  param.w____rRelNm;
                param.ovaccTelNoF =  urzzFunction.fnDateSetter("ddd-dddd-dddd",param.wwpa__o).replaceAll("undefined", ""); 
                param.ovaccRelNmF = param.wwpovaccRelNm; 
                
            }
        } 
        // 전송문자 파라메터를 설정후 반환합니다.
         return urzzFunction.fnFxdfRtnSetter(param,dataUtilLst, colInfoCommon);
     }

    /**
        2023-08-05
        이상근 (YESIAM) 
        수익증권 추가투자(예약)신청서 [402018] 의 항목입력  
    */    
    urzzFunction.fnFxdf__2018 = function(param , dataUtilLst){
        var colInfoCommon = { 
              "UTI_BVB_DSB_ACNO" : "co***noF"
             ,"RPL_DEPS_AMT"       : "ap****F"
             ,"UTI_BVB_DSB_AMT"  : "apl***F"
            
        }
        // 계좌번호에 하이폰을 입력합니다.
        param.connAcnoF = urzObjInfo.transAcno(param.connAcno);
        // 금액
        param.aplAmtF = param.buygAmt.toLocaleString(); 
    
         return urzzFunction.fnFxdfRtnSetter(param,dataUtilLst, colInfoCommon);
    }
        
    /**
        2023-08-05
        이상근 (YESIAM) 
        ****(예약)신청서 [****135] 의 항목입력  
    */    
    urzzFunction.fnFxdf__3135 = function(param , dataUtilLst){
        var colInfoCommon = { 
              "UTI_BVB_DSB_ACNO" : "co****F"
             ,"RPL_DEPS_AMT"       : "ap****F"
             ,"UTI_BVB_DSB_AMT"  : "a****F"
            
        }
        // 계좌번호에 하이폰을 입력합니다.
        param.connAcnoF = urzObjInfo.transAcno(param.connAcno);
        // 금액
        param.aplAmtF = param.buygAmt.toLocaleString(); 

         return urzzFunction.fnFxdfRtnSetter(param,dataUtilLst, colInfoCommon);
    }
    /**
        2023-08-05
        이상근 (YESIAM) 
        투자상품 이슈관리  [401382] 의 항목입력  
    */    
    urzzFunction.fnFxdf__1382 = function(param , dataUtilLst){
        var colInfoCommon = { 
                "MT" : "mm"
        }
        return urzzFunction.fnFxdfRtnSetter(param,dataUtilLst, colInfoCommon);
    }
    /**
        2023-08-05
        이상근 (YESIAM) 
        *** [**1421] 의 항목입력  
    */    
    urzzFunction.fnFxdf__1421 = function(param , dataUtilLst){
        var colInfoCommon = { 
            "WEA_YN_1"     : "val1" 
           ,"POS_SIGN"     : "mmaSign"       
           ,"PRN_LOS_SCLE_LWST_2" : "PRN_LOS_SCLE_LWST"
           ,"PRN_LOS_SCLE_MAX_2"  : "PRN_LOS_SCLE_MAX" 
           ,"PDT_NM_1"     : "pdtNm409143" 
        };
         
         // 전송문자 파라메터를 설정후 반환합니다.
         return urzzFunction.fnFxdfRtnSetter(param,dataUtilLst, colInfoCommon);
    }
    /**
        2023-08-07
        이상근 (YESIAM) 
        *****(**)상품설명서[**9143] 의 항목입력  
    */    
    urzzFunction.fnFxdf__9143 = function(param , dataUtilLst){  
        var colInfoCommon = {
             "PDT_NM"       : "pdtNm409143" 
            ,"POS_SIGN"     : "mmaSign"
        };
        var pdtNm409143Val = "";
        // 투자신청계좌종류가 투자신청계좌단품일경우 
        if(param.ivvSrvyPurpDvcd == 10){ 
            // 투자신청계좌명 + 등급코드를  PDT_NM 에 설정할수 있도록 지정합니다.
            pdtNm409143Val = param.pdtNm + " / " +param.pdtRiskNm;
        }else{ 
            // 여러문건, ***일경우 항목별 내용을 한열로 설정합니다.
            pdtNm409143Val = param.t0.reduce(function(sum, obj){
                return sum + obj.WM_PDT_NM +" (" + obj.UND_GD_DVNM +")/("+obj.UND_GD_DVCD + " 등급) <br>" 
            }, "");
        }
        // 파라메터 설정
        param.pdtNm409143 =pdtNm409143Val
        // 전송문자 파라메터를 설정후 반환합니다.
        return urzzFunction.fnFxdfRtnSetter(param,dataUtilLst, colInfoCommon);
    } 
    /**
        2023-08-05
        *******체크리스트(*****) [**2094] 의 항목입력 
    */
    urzzFunction.fnFxdf__2094 = function(param , dataUtilLst){
        var colInfoCommon = { "VAL_VST"                  : "chkTrgetYnNm1"
                            , "VAL_INFO_NVST_TRND_1"     : "val1" 
                            , "POS_SIGN"                 : "mmaSign"
                            , "NVST_EPL_MENT_1"          : "signedImg1"
                            , "NVST_EPL_MENT_2"          : "signedImg2"
        };   
        
        // 판매일자
        param.ispeDtF = "20yy-mm-dd".replace(/[a-z]{2}/g, function(each){return param[each]});
        //전송문자 파라메터를 설정후 반환합니다.
        return urzzFunction.fnFxdfRtnSetter(param,dataUtilLst, colInfoCommon);
         
    }
     
    
    /**
        2023-08-29
        이상근 (YESIAM) 
        취소 [****123] 의 항목입력 
    */    
    urzzFunction.fnFxdf__1188 = function(param , dataUtilLst){
        var colInfoCommon = {
             "WHO_NM"      : "guestNm"
            ,"ETC_CAN_RSN" : "canCorrRsnNm" 
            ,"WHO_HW_NM"   : "guestHwNm"
            ,"WHO_HW_SIGN" : "guestHwSign"
        };
        
        return urzzFunction.fnFxdfRtnSetter(param,dataUtilLst, colInfoCommon);
    }
    
    
    /**
        2023-08-10
        이상근 (YESIAM) 
        *****상품 설명 확인서 [*****9] 의 항목입력 
    */ 
    urzzFunction.fnFxdf__3159 = function(param , dataUtilLst){ 
        var colInfoCommon = { 
             "UDSD_YN"           : "val1"
            ,"WHO_EXPOSER_TTLE"  : "davDataTemp"  
        }; 
 
        var colInfoAddNum = {
             "SHPNM"        : "deptName"
            ,"POS_PSINM"    : "mmaPsitCdnm" 
            ,"POS_SIGN"     : "mmaSign" 
        }
        
        colInfoCommon.VAL_NM_1 = "guestHwNm" 
        colInfoCommon.VAL_SIGN_1 = "guestHwSign"
        
        // 중복되어지는 항목의 값을 1,2,3 으로 지정합니다.
        for(var i = 1 ; i <=2 ; i++){
            Object.keys(colInfoAddNum).forEach(function(key){
                colInfoCommon[key + "_" + i] = colInfoAddNum[key];t
            })
        }
        param.exposerTtleF = param.pdtNm + " / " +param.pdtRiskNm; 
     
        // 65세 이상일경우 
        if(param.age >= 65){  
            colInfoCommon.VAL_NM_2 = "guestHwNm" 
            colInfoCommon.VAL_SIGN_2 = "guestHwSign" 
            
            if(["04","08"].indexOf(param.outsReptVbaaClacd) != -1){
                for(var i = 1 ; i<=5; i++){
                    colInfoCommon["MNGM_RPPR_CHK_LST_YN_"+i] = "val1"
                }
                colInfoCommon.VAL_NM_3 = "guestHwNm"
                colInfoCommon.VAL_SIGN_3 = "guestHwSign"  
                colInfoCommon.MNGM_RPPR_STBT_OPNN_YN = "val1"
                 colInfoCommon.WEA_YR_4 = "yy"
                colInfoCommon.WEA_MNTH_4 = "mm"
                colInfoCommon.WEA_DD_4 = "dd"
            }
            
            // **자 여부가 1일경우 
            if(param.wwpovaccLemeSvYn == "1"){ 
                param.ovaccRelSignF =  param.wwpovaccRelSign;
                param.ovaccRelNmF =  param.wwpovaccRelNm;
                param.ovaccTelNoF =  urzzFunction.fnDateSetter("ddd-dddd-dddd",param.ovaccTelNo).replaceAll("undefined", ""); 
                param.ovaccRelNmF = param.ovaccRelNm;  
            }
        } 
        else {
            colInfoCommon.MNGM_RPPR_POS_PSIT = "";
        }
        return  urzzFunction.fnFxdfRtnSetter(param, dataUtilLst ,colInfoCommon );
    }
    /**
        2023-08-05
        이상근 (YESIAM) 
        **상품 신규가입신청서[4****7] 의 ********입력 항목입력 
    */
    urzzFunction.fnFxdf__2017 = function(param , dataUtilLst){discuseAAA
        // *******신청서[******] 에 공통으로 들어갈 데이터를 설정합니다.
        // 공통적으로 들어갈 컬럼입니다
        // param 항목명 마지막에 대문자 F 가 입력되어진 항목은 화면에 표시될 포멧이 적용 되어진 항목입니다.
        var colInfoCommon = {
              "EXPOSER_VAL_DFRY_ACNO"      : "exporserDfryAcnoF"      
            , "EXPOSER_VAL_EGM_DT"         : "exporserPymtEgmDdF"     
            , "EXPOSER_VAL_AMT"            : "exporserEgmAmtF"        
            , "EXPOSER_VAL_STYM"           : "exporserPymtStrdtF"     
            , "EXPOSER_VAL_EDYM"           : "exporserPymtEdtF"       
            , "EXPOSER_VAL_NP_SMS_NOTI_YN" : "exporserComptYn"        
            , "EXPOSER_UPLMT_ENRT"         : "smsNotiUplmtEnrt"     
            , "EXPOSER_AUTO_RBUY_SV_1"     : "smatAutoRbuyEgmYn"    
        };
        // 투자신청계좌종류별로 지정해야할 파라메터를 설정합니다.
        var colInfoJS = { "10" : { "RS_EXPOSER_NM"            : "pdtNm"          
                                 , "RS_EXPOSER_SVNG_MTD"      : "svngMtd"                  
                                 , "RS_EXPOSER_VAL_SIGN_2"    : "guestHwSign"
                                 , "RS_EXPOSER_NEW_NVST_AMT"  : "buygAmtF"
                                 , "NTAXN_PRSNL_INFO_CLTN_U_AG_YN" : "prsnlInfoCltnUAgYn"                                                                     
                                 }
                        , "20" : { "OOVF_DSN_TYPE"          : "prtfDsnType"             
                                 , "OOVF_SVNG_MTD"          : "svngMtd"                   
                                 , "OOVF_CONN_ACNO"         : "connAcnoF"       
                                 , "OOVF_EXPOSER_JN_DD"     : "dd"             
                                 , "OOVF_VAL_SIGN_2"        : "guestHwSign"     
                                 , "OOVF_VAL_NM_2"          : "guestHwNm"
                                 , "RBC_LEME_APL_YN"        : "prtfLmsNotiYn"   
                                 }
                        }
                         
        // 투자신청계좌 종류별로 데이터를 가져오긴하는데 ***일경우 20을 공통으로 사용합니다

        var runDataList = !colInfoJS[param.ivvSrvyPurpDvcd] ? colInfoJS["20"] : colInfoJS[param.ivvSrvyPurpDvcd];
        Object.assign(runDataList,colInfoCommon);
    
        param.prtfBnkbNyPblAg20 = "0"
        // 데이터를 설정합니다.
        // 저축종류 구분을 설정합니다. svngKndDvcd { "04":"자**식", "03":"정액적립식" ,"02":"거치식" ,'01':"임의식" }
        // 서식화면에서는 임이식(01) : 1 , 거치식(02) :2, 자유적립식(04) :"3" 으로 설정합니다.
        var svngKndDvcdJs = {"1*" : "1" , "9a" :"2" , "**" : "3"  } 
        // 설정할 값을 param.svngMtd 에 새로 지정합니다.

        param.svngMtd = svngKndDvcdJs[param.svngKndDvcd];
        
        // 연결계좌번호에 하이폰을 입력합니다.
        param.connAcnoF = urzObjInfo.transAcno(param.connAcno);
        // 계약기간 
        param.ctrcPridMtF = !param.exdt ? "" :  (param.ctrcPridMt*1) +" 개월 "+ urzzFunction.fnDateSetter("(~ yyyy년mm월dd일)",param.exdt)  ;
        // 통장미발행관련 은 1을 입력합니다.
    
        // 목표수익술 문자통지
        if(param.smsEnrtAttaFwTrgetYn != "1"){
            param.smsNotiUplmtEnrt ="";
            param.smsNotiLwlmEnrt ="";
        }  
        //자동이체 여부가 1일경우
        if(param.exporserYn == "1"){
            // 출**좌 항목에 하이폰추가합니다.
             param.exporserDfryAcnoF = urzObjInfo.transAcno(param.exporserDfryAcno);
             // 자동이체 여부가 1일경우 약정일을 표시합니다. 
             param.exporserPymtEgmDdF = "매월 @day일".replace("@day", param.exporserPymtEgmDd =="31" ? "말" : param.exporserPymtEgmDd);
             // 시작년월
             param.exporserPymtStrdtF = !param["exporserPymtStrdt"] ? "" : urzzFunction.fnDateSetter("yyyy년mm월",param["exporserPymtStrdt"]);
            param.exporserPymtEdtF = !param["exporserPymtEdt"] ? "" : urzzFunction.fnDateSetter("yyyy년mm월",param["exporserPymtEdt"]);
            // 자동이체금액
            param.exporserEgmAmtF = (param.exporserEgmAmt*1).toLocaleString() +" 원";
            
        }
        // 투자신******서비스 
        if(param.smatAutoRbuyEgmYn != "1"){ 
            // ** , 목***
            param.trgHiAttaRt = "" 
            param.trgLwstAttaRt = "" 
 
        } 
        // 투자내용확인
        param.exposerCntnVd = "1"; 
         //**** 설정
        param.astOprReprtNtiMtd =  param.oprReprtNotiEgmDvcd == "3" ? "1": (param.oprReprtNotiEgmDvcd =="1" ? "2" : (param.oprReprtNotiEgmDvcd=="2" ? "3" : (param.oprReprtNotiEgmDvcd=="5"? "4":"") ) );
        // ** 수익율  ***_SMS(C) ,실질**률_E-mail(b), 실질***략(a)
        param.essnNvstEnrtNtiMtd = param.smsNotiYn == "0" ? "3" : "1" 
        //신분증 여부 확인
        param.discuseAAA = urzObjInfo.getBizCusComInfo("discuseAAA");
        param.discuseAAA = !param.discuseAAA ? "0" : param.discuseAAA
        var idcrdScanText = { "0":"별도스캔", "1":"신분증스캐너스캔필", "2":"지정맥인증"}
        param.discuseAAAF = idcrdScanText[param.discuseAAA];
        // ****** 관련
        param.prtfLmsNotiYn = !param.prtfLmsNotiYn ? "0" : param.prtfLmsNotiYn;
        // ****계좌 혹은 ***일경우 일괄적으로 데이터를 설정합니다.
         if(param.ivvSrvyPurpDvcd != "10"){
             // 여러문건 ***일경우 
             //집합투자상품 ****입신청서[*****] 일경 *****보 입력
             var EV_IPC = {pdtNm : 'OOVF_EXPOSER_NM', rplDepsAmt : 'OOVF_NEW_NVST_AMT', exporserEgmAmt :'OOVF_VAL_AMT'};
             var EV_IPCList = param.ivvSrvyPurpDvcd == 30 ? param.t0 : param.t0;  
             EV_IPCList.forEach(function(eachInfo, idx){ 
                 // 인덱스 +1 을하여 데이터를 입력합니다.
                var nowIdx = idx+1;
                 Object.keys(EV_IPC).forEach(function(tsObj){
                     // 전송문자 파라메터 생성
                     var temJson = {};
                     temJson.pname =  tsObj+"_"+nowIdx;
                     temJson.utilNm = [(EV_IPC[tsObj]+"_"+nowIdx)]; 
                     runDataList[temJson.utilNm] = temJson.pname ;
                     // 파라메터 설정
                     var tempRsVal = eachInfo[tsObj];
                     // Amt 가 들어가는 행목이 있을경우 '###,###,### 원' 포멧을 적용하니다.
                     param[temJson.pname] = tsObj.indexOf("Amt") != -1 ? (tempRsVal*1).toLocaleString() +" 원" : tempRsVal ;
                 });
             });
             // **** 신규가입신청서[*******] 일경 투자신청계좌정보 입력
             // 여러문건 가입란의 항목을 지정합니다. 여러문건일경우 1 , *** 2 
             param.prtfDsnType = param.ivvSrvyPurpDvcd == 30 ? "2" : "1"
             // 로봇 *** 
             param.prtfBnkbNyPblAg20 = "1"
             param.val0 = "0"; 
         }else{
             // 투자*********경우
             /* 비********정보를 입력합니다.*/
             var isVersiJarvart =   param.txsysJarvartKndcd != "00" 
            param.txsysJarvartKndcdVal = isVersiJarvart ?  "1" : "0";
            // *** 입력시 만원 단위로 표시되어지게 합니다.
            param.hhbkLamtVal = isVersiJarvart ? param.hhbkLamt/10000 : "";
            // *** 수집동의  ***경우 무조건 1로 설정 없을경우 null로 진행합니다.
            param.txsysJarvartYn = isVersiJarvart ? "1" : "";        
            // 비과세 서명
            param.unyVbaaNvstorNm1 = isVersiJarvart ? param.guestHwNm : "";
            param.unyVbaaNvstorSign1 = isVersiJarvart ? param.guestHwSign : "";            
            /* 비과세 ****정보 끝*/
            param.buygAmtF = param.buygAmt.toLocaleString() +" 원" 
            
         }
         //전송문자 파라메터를 설정후 반환합니다.
         return  urzzFunction.fnFxdfRtnSetter(param, dataUtilLst ,runDataList );
    }
    
    
    /**
        2023-08-07
        이상근 (YESIAM) 
        전송문자 파라메터를 설정합니다. 합니다.
    */    
    urzzFunction.fnFxdfRtnSetter = function(param, dataUtilLst, colInfoCommon ){ 
        // 3번? 파라머테 colInfoCommon 항목의 키 기준으로 데이터를 설정합니다.
        Object.keys(colInfoCommon).forEach(function(key){
            // 표시할데이터 미리설정
            var dataUtil = {pname : colInfoCommon[key] ,utilNm : [key]}; 
            // 데이터가 있는지 확인합니다.
            var fxdTemp = dataUtilLst.find(function(obj){return obj.pname == dataUtil.pname});
            // 없을경우 신규로 추가 
            if(!fxdTemp){
                dataUtilLst.push(dataUtil);
            }else{ 
                // 이미 서식에 표시할 컬럼이 있을경우 전송문자 키값 기준 데이터를 추가합니다.
                dataUtilLst.map(function(obj){
                    if(obj.pname == dataUtil.pname){
                        // 데이터를 병합후 중복을 제거합니다.
                        obj.utilNm = obj.utilNm.concat(dataUtil.utilNm); 
                        obj.utilNm = Object.keys(Object.fromEntries(obj.utilNm.map(function(v){return [v,0]})));
                    } 
                    return obj;
                });
            }  
        });  
        return {"param" : param , "dataUtil" : dataUtilLst};
    }

    urzzFunction.TXT_LOW_VAL = { title :"**약정 확인"
                               , cont  :"**수약정을 확인해주세요. <br> 최저액수약정 : @val 원"}
    
    urzzFunction.fnOpenMsgPopLowVal = function(mainObjObj ,val){
        var paraJSON = {BOXTYPE:"INFOMATION"
                       ,title:urzzFunction.TXT_LOW_VAL.title
                       ,msg:urzzFunction.TXT_LOW_VAL.cont.replace("@val",(val*1).toLocaleString())};
        urzzFunction.openMessagePopup(mainObjObj, paraJSON );
        return true;
        
    }
    
 
    
    
    
    
    

    
