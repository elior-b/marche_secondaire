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
		var barChartData = {
				labels : ["January","February","March","April","May","June","July"],
				datasets : [
					{
						fillColor : "rgba(220,220,220,0.5)",
						strokeColor : "rgba(220,220,220,0.8)",
						highlightFill: "rgba(220,220,220,0.75)",
						highlightStroke: "rgba(220,220,220,1)",
						data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
					},
					{
						fillColor : "rgba(151,187,205,0.5)",
						strokeColor : "rgba(151,187,205,0.8)",
						highlightFill : "rgba(151,187,205,0.75)",
						highlightStroke : "rgba(151,187,205,1)",
						data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
					}
				]

			}



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
        	   idToShow = $(this).attr('data-id');
               idToHide = $(".nav-tabs .active").find('a').attr('data-id');
           $(".nav-tabs .active").removeAttr('class');
           $(this).parent().attr('class','active');
           $('#'+idToHide).css('display','none');
           $('#'+idToShow).css('display','block');
           if(idToShow === "societe-graphiques"){
               var lineChart = document.getElementById("lineChart-canvas").getContext("2d");
               window.myLine = new Chart(lineChart).Line(lineChartData, {responsive: true});
               var barChart = document.getElementById("barChart-canvas").getContext("2d");
               window.myBar = new Chart(barChart).Bar(barChartData, {responsive : true});
           }
            });
    });