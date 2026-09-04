scoreboard players add @s aj.ajemissary.animation.phase3.attack.flame_slash.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajemissary.animation.phase3.attack.flame_slash.local_anim_time
function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.flame_slash/apply_frame_as_root
execute if score @s aj.ajemissary.animation.phase3.attack.flame_slash.local_anim_time matches 40.. run function animated_java:ajemissary/zzzzzzzz/animations/phase3.attack.flame_slash/end