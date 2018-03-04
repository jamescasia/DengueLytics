const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
var totalcases; 
var db = admin.database();
var reference = db.ref("Functions/DengueTracking/totalcases")
 


reference.on("value", function(snapshot) {
  console.log(snapshot.val());
    totalcases = snapshot.val()
}, function (errorObject) {
  console.log("The read failed: " + errorObject.code);
});
 
exports.addCase = functions.database.ref("/Functions/DengueTracking/reports/{pushId}").onCreate(event =>
	{

		var report = event.data.val()
		if(report.timeadded)
		{
			return
		} 


		var timeofreport =  new Date() 
		var propertimeofreport = new Date()
		report.timeadded = true
		report.time =Math.round( timeofreport.getTime())
		 
		//event.data.ref.parent.child('time').set(timeofreport)

		//event.data.ref.parent.parent.child('time').set(timeofreport)
		totalcases = totalcases+report.cases


		
		event.data.ref.parent.parent.child('totalcases').push(totalcases)
		//var eventid = event.data.ref.parent.parent.child('vertices').child('time').push().key
		//event.data.ref.parent.parent.child('vertices').child('time').child(eventid).set(report.time)
		//event.data.ref.parent.parent.child('vertices').child('totalcases').child(eventid).set(totalcases)
		


		//child(timeofreport).set(new Date())
		var eventid = event.data.ref.parent.parent.child('vertices').push().key
		event.data.ref.parent.parent.child('vertices').child(eventid).child('time').set(report.time)
		event.data.ref.parent.parent.child('vertices').child(eventid).child('totalcases').set(totalcases)


 
//		 event.data.ref.parent.parent.child('ProperTime').push ('fasdasdat')
	//	event.data.ref.parent.parent.push('llll')

//toLocaleTimeString("en_US", options)
		event.data.ref.parent.parent.child('ProperTime').push().set(timeofreport.toLocaleString())
	//	event.data.ref.parent.parent.child('ProperTime').child(timeid) .set(timeofreport)

		return event.data.ref.set(report)





	}); 

 