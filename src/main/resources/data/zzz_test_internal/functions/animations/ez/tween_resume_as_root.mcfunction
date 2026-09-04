function zzz_test_internal:animations/ez/resume_as_root
function zzz_test_internal:animations/ez/tween_as_root
function zzz_test_internal:animations/ez/apply_frame_as_root
execute if score #tween_duration aj.i matches ..0 on passengers store result entity @s interpolation_duration int 1 run scoreboard players get $aj.default_interpolation_duration aj.i
scoreboard players reset #tween_duration aj.i