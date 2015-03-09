var randomScalingFactor = function(){ return Math.round(Math.random()*100)};
		var lineChartData = {
			labels : ["January","February","March","April","May","June","July"],
			datasets : [
				{
					label: "My First dataset",
					fillColor : "rgba(220,220,220,0.2)",
					strokeColor : "rgba(220,220,220,1)",
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(220,220,220,1)",
					data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
				},
				{
					label: "My Second dataset",
					fillColor : "rgba(151,187,205,0.2)",
					strokeColor : "rgba(151,187,205,1)",
					pointColor : "rgba(151,187,205,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(151,187,205,1)",
					data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
				}
			]

		}
        var doughnutData = [
				{
					value: 300,
					color:"#F7464A",
					highlight: "#FF5A5E",
					label: "Red"
				},
				{
					value: 50,
					color: "#46BFBD",
					highlight: "#5AD3D1",
					label: "Green"
				},
				{
					value: 100,
					color: "#FDB45C",
					highlight: "#FFC870",
					label: "Yellow"
				},
				{
					value: 40,
					color: "#949FB1",
					highlight: "#A8B3C5",
					label: "Grey"
				},
				{
					value: 120,
					color: "#4D5360",
					highlight: "#616774",
					label: "Dark Grey"
				}

			];


$(function() {
        $('[data-toggle="tooltip"]').tooltip();
    
        $('body').on('change', "#tata\\:toto", function() {
            var valeur = $(this).val();
            $('.colors').hide();
            $('#' + valeur).show();
        });
        $('body').on('click', ".btn-enregistrement", function() {
                $('#presentation-index').css('display','none');
                $('#enregistrement-index').css('display','block');
            });
        $('body').on('click', ".nav-tabs li a", function() {
                valeur = $(this).attr('id');
            $(".nav-tabs .active").removeAttr('class');
            $(this).parent().attr('class','active');
            if(valeur === "data"){
                $('#company-index').css('display','none');
                $('#company-plot').css('display','none');
                $('#company-data').css('display','block');
                
            }else if(valeur === "plot"){
                $('#company-index').css('display','none');
                $('#company-data').css('display','none');
                $('#company-plot').css('display','block');
                var lineChart = document.getElementById("canvas").getContext("2d");
                window.myLine = new Chart(lineChart).Line(lineChartData, {responsive: true});
                var doughnutChart = document.getElementById("chart-area").getContext("2d");
				window.myDoughnut = new Chart(doughnutChart).Doughnut(doughnutData, {responsive : true});
            }else{
                $('#company-data').css('display','none');
                $('#company-plot').css('display','none');
                $('#company-index').css('display','block');
            }
            });
    });