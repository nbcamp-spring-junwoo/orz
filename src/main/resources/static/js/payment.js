const coupon = document.getElementById("coupon-box");
const button = document.getElementById("payment-button");
const amount = 50000;

// 구매자의 고유 아이디를 불러와서 customerKey로 설정하세요.
// 이메일・전화번호와 같이 유추가 가능한 값은 안전하지 않습니다.
const widgetClientKey = "test_ck_pP2YxJ4K87Xd5B5xQRnL3RGZwXLO";
const customerKey = "NbR5egahSs__VmAgS54e1";
const paymentWidget = PaymentWidget(widgetClientKey, customerKey); // 회원 결제

const paymentMethodWidget = paymentWidget.renderPaymentMethods("#payment-method", {value: amount}, {variantKey: "DEFAULT"});

paymentWidget.renderAgreement("#agreement", {variantKey: "AGREEMENT"});

coupon.addEventListener("change", function () {
    if (coupon.checked) {
        paymentMethodWidget.updateAmount(amount - 5000);
    } else {
        paymentMethodWidget.updateAmount(amount);
    }
});

button.addEventListener("click", function () {
    // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
    // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
    console.log("click");
    paymentWidget.requestPayment({
        orderId: "1W_pCfO4rzG9szJEcThKe",
        orderName: "토스 티셔츠 외 2건",
        successUrl: window.location.origin + "/payment/success.html",
        failUrl: window.location.origin + "/payment/fail.html",
        customerEmail: "customer123@gmail.com",
        customerName: "김토스",
        customerMobilePhone: "01012341234",
    });
});
