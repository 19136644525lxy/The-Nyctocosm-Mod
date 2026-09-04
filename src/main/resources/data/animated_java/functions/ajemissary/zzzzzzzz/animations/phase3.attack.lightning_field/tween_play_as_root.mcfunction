function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.lightning_field/play_as_root
function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.lightning_field/tween_as_root
execute if score #tween_duration aj.i matches ..0 on passengers run data modify entity @s interpolation_duration set value 1
scoreboard players reset #tween_duration aj.i