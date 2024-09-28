package com.shop.food.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResponseCode {
    CODE_00005("00005", "Vui lòng cung cấp đầy đủ thông tin để gửi mã."),
    CODE_00006("00006", "Truy cập bị từ chối. Không có token được cung cấp."),
    CODE_00007("00007", "ID người dùng không hợp lệ."),
    CODE_00008("00008", "Đã xảy ra lỗi máy chủ nội bộ, vui lòng thử lại."),
    CODE_00009("00009", "Không thể tìm thấy người dùng đã xác minh với mã và ID được cung cấp."),
    CODE_00011("00011", "Phiên của bạn đã hết hạn, vui lòng đăng nhập lại."),
    CODE_00012("00012", "Token không hợp lệ. Token có thể đã hết hạn."),
    CODE_00017("00017", "Truy cập bị từ chối. Bạn không có quyền truy cập."),
    CODE_00019("00019", "Truy cập bị từ chối. Bạn không có quyền truy cập."),
    CODE_00021("00021", "Truy cập bị từ chối. Bạn không có quyền truy cập."),
    CODE_00022("00022", "Không có ID được cung cấp trong tham số. Vui lòng nhập một ID."),
    CODE_00023("00023", "ID được cung cấp không phải là một đối tượng ID hợp lệ."),
    CODE_00024("00024", "Quá nhiều yêu cầu."),
    CODE_00025("00025", "Vui lòng cung cấp tất cả các trường bắt buộc!"),
    CODE_00026("00026", "Vui lòng cung cấp một địa chỉ email hợp lệ!"),
    CODE_00027("00027", "Vui lòng cung cấp mật khẩu dài hơn 6 ký tự và ngắn hơn 20 ký tự."),
    CODE_00028("00028", "Vui lòng cung cấp một tên dài hơn 3 ký tự và ngắn hơn 30 ký tự."),
    CODE_00029("00029", "Vui lòng cung cấp một địa chỉ email hợp lệ!"),
    CODE_00032("00032", "Một tài khoản với địa chỉ email này đã tồn tại."),
    CODE_00035("00035", "Bạn đã đăng ký thành công."),
    CODE_00036("00036", "Không tìm thấy tài khoản với địa chỉ email này."),
    CODE_00038("00038", "Vui lòng cung cấp tất cả các trường bắt buộc!"),
    CODE_00039("00039", "Vui lòng cung cấp một địa chỉ email hợp lệ!"),
    CODE_00040("00040", "Vui lòng cung cấp mật khẩu dài hơn 6 ký tự và ngắn hơn 20 ký tự."),
    CODE_00042("00042", "Không tìm thấy tài khoản với địa chỉ email này."),
    CODE_00043("00043", "Email của bạn chưa được kích hoạt, vui lòng đăng ký trước."),
    CODE_00044("00044", "Email của bạn chưa được xác minh, vui lòng xác minh email của bạn."),
    CODE_00045("00045", "Bạn đã nhập một email hoặc mật khẩu không hợp lệ."),
    CODE_00047("00047", "Bạn đã đăng nhập thành công."),
    CODE_00048("00048", "Mã đã được gửi đến email của bạn thành công."),
    CODE_00050("00050", "Đăng xuất thành công."),
    CODE_00052("00052", "Không thể tìm thấy người dùng."),
    CODE_00053("00053", "Vui lòng gửi một mã xác nhận."),
    CODE_00054("00054", "Mã bạn nhập không khớp với mã chúng tôi đã gửi đến email của bạn."),
    CODE_00055("00055", "Token không hợp lệ. Token có thể đã hết hạn."),
    CODE_00058("00058", "Địa chỉ email của bạn đã được xác minh thành công."),
    CODE_00059("00059", "Vui lòng cung cấp token làm mới."),
    CODE_00061("00061", "Token được cung cấp không khớp với người dùng, vui lòng đăng nhập."),
    CODE_00062("00062", "Token đã hết hạn, vui lòng đăng nhập."),
    CODE_00063("00063", "Không thể xác minh token, vui lòng đăng nhập."),
    CODE_00065("00065", "Token đã được làm mới thành công."),
    CODE_00066("00066", "Vui lòng cung cấp một mật khẩu dài hơn 6 và ngắn hơn 20 ký tự."),
    CODE_00068("00068", "Mật khẩu mới đã được tạo thành công."),
    CODE_00069("00069", "Vui lòng cung cấp mật khẩu cũ và mới dài hơn 6 ký tự và ngắn hơn 20 ký tự."),
    CODE_00072("00072", "Mật khẩu cũ của bạn không khớp với mật khẩu bạn nhập, vui lòng nhập mật khẩu đúng."),
    CODE_00073("00073", "Mật khẩu mới của bạn không nên giống với mật khẩu cũ, vui lòng thử một mật khẩu khác."),
    CODE_00076("00076", "Mật khẩu của bạn đã được thay đổi thành công."),
    CODE_00077("00077", "Vui lòng cung cấp một tên dài hơn 3 ký tự và ngắn hơn 30 ký tự."),
    CODE_00078("00078", "Các tùy chọn giới tính hợp lệ, female-male-other, vui lòng cung cấp một trong số chúng."),
    CODE_00079("00079", "Các tùy chọn ngôn ngữ hợp lệ, tr-en, vui lòng cung cấp một trong số chúng."),
    CODE_00080("00080", "Vui lòng cung cấp một ngày sinh hợp lệ."),
    CODE_00081("00081", "Vui lòng cung cấp một tên người dùng dài hơn 3 ký tự và ngắn hơn 15 ký tự."),
    CODE_00084("00084", "Đã có một người dùng với tên người dùng này, vui lòng nhập tên khác."),
    CODE_00086("00086", "Thông tin hồ sơ của bạn đã được thay đổi thành công."),
    CODE_00089("00089", "Thông tin người dùng đã được lấy thành công."),
    CODE_00092("00092", "Tài khoản của bạn đã bị xóa thành công."),
    CODE_00093("00093", "Không thể tạo nhóm, bạn đã thuộc về một nhóm rồi."),
    CODE_00095("00095", "Tạo nhóm thành công."),
    CODE_00096("00096", "Bạn không thuộc về nhóm nào."),
    CODE_00098("00098", "Thành công."),
    CODE_00099("00099", "Người này đã thuộc về một nhóm."),
    CODE_00099x("00099x", "Không tồn tại user này."),
    CODE_00100("00100", "Thiếu username."),
    CODE_00102("00102", "Người dùng thêm vào nhóm thành công."),
    CODE_00103("00103", "Người này chưa vào nhóm nào."),
    CODE_00104("00104", "Bạn không phải admin, không thể xóa."),
    CODE_00106("00106", "Xóa thành công."),
    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
