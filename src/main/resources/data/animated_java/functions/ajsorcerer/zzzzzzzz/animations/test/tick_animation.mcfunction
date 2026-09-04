scoreboard players add @s aj.ajsorcerer.animation.test.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajsorcerer.animation.test.local_anim_time
function animated_java:ajsorcerer/zzzzzzzz/animations/test/apply_frame_as_root
execute if score @s aj.ajsorcerer.animation.test.local_anim_time matches 1.. run function animated_java:ajsorcerer/zzzzzzzz/animations/test/end