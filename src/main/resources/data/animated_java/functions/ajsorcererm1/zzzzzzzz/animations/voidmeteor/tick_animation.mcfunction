scoreboard players add @s aj.ajsorcererm1.animation.voidmeteor.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajsorcererm1.animation.voidmeteor.local_anim_time
function animated_java:ajsorcererm1/zzzzzzzz/animations/voidmeteor/apply_frame_as_root
execute if score @s aj.ajsorcererm1.animation.voidmeteor.local_anim_time matches 66.. run function animated_java:ajsorcererm1/zzzzzzzz/animations/voidmeteor/end