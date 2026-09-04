function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.sword_plunge/resume_as_root
function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.sword_plunge/tween_as_root
function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.sword_plunge/apply_frame_as_root
execute if score #tween_duration aj.i matches ..0 on passengers run data modify entity @s interpolation_duration set value 1
scoreboard players reset #tween_duration aj.i