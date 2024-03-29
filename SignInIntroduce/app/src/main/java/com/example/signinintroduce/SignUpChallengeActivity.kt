package com.example.signinintroduce

import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.signinintroduce.databinding.ActivitySignUpChallengeBinding

class SignUpChallengeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpChallengeBinding
    private lateinit var viewModel: SignUpViewModel

    companion object {
        // 나중엔 서버에서 받아오는 식.
        val mails = arrayOf("gmail.com", "kakao.com", "naver.com", "직접 입력")
    }

    private val anim = AlphaAnimation(0.125f, 0.625f).apply {
        duration = 100L
        repeatCount = 3
    }

    // EditText 배열
    private val arrET by lazy {
        arrayOf(
            binding.etChallName,
            binding.etChallMail,
            binding.etChallDomain,
            binding.etChallPassword,
            binding.etChallVerify
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        initViewModel(this)

        binding.spnrChallMail.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mails)

        initListeners()
    }

    private fun initViewModel(ac: AppCompatActivity) = viewModel.apply {
        btnChallSignupIsEnabled.observe(ac) { binding.btnChallSignup.isEnabled = it }
        spnrChallMailIsVisible.observe(ac) { binding.spnrChallMail.isVisible = it }
        tvChallPasswordWarnText.observe(ac) { binding.tvChallPasswordWarn.text = it }
        tvChallPasswordWarnStartAnimation.observe(ac) {
            if (!it) return@observe
            setTvChallPasswordWarnStartAnimation(false)
            binding.tvChallPasswordWarn.startAnimation(anim)
        }
        etChallPasswordSetSelection.observe(ac) { binding.etChallPassword.setSelection(it) }
        tvChallPasswordLengthText.observe(ac) { binding.tvChallPasswordLength.text = it }
        tvChallNameWarnText.observe(ac) { binding.tvChallNameWarn.text = it }
        tvChallMailWarnText.observe(ac) { binding.tvChallMailWarn.text = it }
        tvMailWarnText.observe(ac) { binding.tvChallMailWarn.text = it }
        tvChallVerifyWarnText.observe(ac) { binding.tvChallVerifyWarn.text = it }
    }

    private fun initListeners() {
        arrET.forEach { et ->
            et.addTextChangedListener {
                viewModel.check(
                    viewToEnum(et),
                    et.text.toString()
                )
            }
        }
        val onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) viewModel.check(
                    viewToEnum(v),
                    (v as EditText).text.toString()
                )
            }
        arrET.forEach { it.onFocusChangeListener = onFocusChangeListener }

        binding.btnChallSignup.setOnClickListener {
            if (binding.spnrChallMail.isVisible) {
                intent.putExtra(
                    Extra.id,
                    "${binding.etChallMail.text}@${binding.spnrChallMail.selectedItem}"
                )
            } else {
                intent.putExtra(
                    Extra.id,
                    "${binding.etChallMail.text}@${binding.etChallDomain.text}"
                )
            }
            intent.putExtra(Extra.password, binding.etChallPassword.text.toString())
            // TODO: 뷰모델에서 체크를 해야한다. 그냥 스타트 액티비티나 셋리절트 해버리면 문제가 있을 수 있다.
            // 서버에 유저 정보를 적재하고. 결과를 확인해서. 결과값을 라이브데이터에 저장해두고.
            // 추후에 확인해서 액티비티 넘어가기?
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.spnrChallMail.onItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    if (position == binding.spnrChallMail.adapter.count - 1) {
                        binding.etChallDomain.isVisible = true
                        binding.spnrChallMail.isVisible = false
                    }

                    viewModel.check(viewToEnum(binding.spnrChallMail))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

//    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()

    private fun viewToEnum(v: View): EnumView = when (v) {
        binding.etChallName -> EnumView.ET_CHALL_NAME
        binding.etChallMail -> EnumView.ET_CHALL_MAIL
        binding.etChallDomain -> EnumView.ET_CHALL_DOMAIN
        binding.etChallPassword -> EnumView.ET_CHALL_PASSWORD
        binding.etChallVerify -> EnumView.ET_CHALL_VERIFY
        else -> EnumView.END
    }
}