'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.sendMessageNotification = functions.database.ref('/messages/{messageNode}/{message}').onWrite(
event=>{
	const messageNode = event.params.messageNode;
	const message = event.params.message;


	console.log('messageNode:', messageNode, 'message', message);

	const messageVal = event.data.val();

	const receiverUid = messageVal.receiverUid;
	const senderUid = messageVal.senderUid;

	console.log('receiverUid:', receiverUid, 'senderUid:', senderUid);	


    const getInstanceIdPromise = admin.database().ref(`/users/${receiverUid}/instanceId`).once('value');

    const getReceiverUidPromise = admin.auth().getUser(receiverUid);


    return Promise.all([getInstanceIdPromise, getReceiverUidPromise]).then(results => {
            const instanceId = results[0].val();
            const receiver = results[1];
            console.log('notifying ' + receiverUid + ' about ' + messageVal.data + ' from ' + senderUid);

            const payload = {
         		data : messageVal
            };

            admin.messaging().sendToDevice(instanceId, payload)
                .then(function (response) {
                    console.log("Successfully sent message:", response);
                })
                .catch(function (error) {
                    console.log("Error sending message:", error);
                });
        });
	
	/*
	const dataPayload = {
		data: {
			score : "850",
			time : "2:45"
		}
	};


	admin.messaging().sendToDevice(instanceId, dataPayload)
	.then(function (response){
		console.log("successfully sent message:", response);
	})
	.catch(function (error){
		console.log("error sending message:", response);

	});
	*/
		
});

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
