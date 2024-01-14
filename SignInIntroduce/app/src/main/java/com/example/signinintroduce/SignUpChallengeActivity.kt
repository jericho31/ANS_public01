package com.example.signinintroduce

import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
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
        initViewModel(viewModel)

        binding.spnrChallMail.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mails)

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

    private fun initViewModel(vm: SignUpViewModel) {
        vm.btnChallSignupIsEnabled.observe(this) { binding.btnChallSignup.isEnabled = it }
        vm.spnrChallMailIsVisible.observe(this) { binding.spnrChallMail.isVisible = it }
        vm.etChallPasswordText.observe(this) { binding.etChallPassword.setText(it) }
        vm.tvChallPasswordWarnText.observe(this) { binding.tvChallPasswordWarn.text = it }
        vm.tvChallPasswordWarnStartAnimation.observe(this) {
            if (!it) return@observe
            vm.setTvChallPasswordWarnStartAnimation(false)
            binding.tvChallPasswordWarn.startAnimation(anim)
        }
        vm.etChallPasswordSetSelection.observe(this) { binding.etChallPassword.setSelection(it) }
        vm.tvChallPasswordLengthText.observe(this) { binding.tvChallPasswordLength.text = it }
        vm.etChallNameText.observe(this) { binding.etChallName.text }
        vm.tvChallNameWarnText.observe(this) { binding.tvChallNameWarn.text = it }
        vm.tvChallMailWarnText.observe(this) { binding.tvChallMailWarn.text = it }
        vm.spinnerIsVisible.observe(this) { binding.spnrChallMail.isVisible = it }
        vm.tvMailWarnText.observe(this) { binding.tvChallMailWarn.text = it }
        vm.tvChallVerifyWarnText.observe(this) { binding.tvChallVerifyWarn.text = it }
    }


    private fun toastShort(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()

    fun viewToEnum(v: View): EnumView = when (v) {
        binding.etChallName -> EnumView.ET_CHALL_NAME
        binding.etChallMail -> EnumView.ET_CHALL_MAIL
        binding.etChallDomain -> EnumView.ET_CHALL_DOMAIN
        binding.etChallPassword -> EnumView.ET_CHALL_PASSWORD
        binding.etChallVerify -> EnumView.ET_CHALL_VERIFY
        else -> EnumView.END
    }
}