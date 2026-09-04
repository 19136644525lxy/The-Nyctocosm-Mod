scoreboard players add @s aj.ajemissary.animation.phase2.attack.wing_blast.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajemissary.animation.phase2.attack.wing_blast.local_anim_time
function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.wing_blast/apply_frame_as_root
execute if score @s aj.ajemissary.animation.phase2.attack.wing_blast.local_anim_time matches 80.. run function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.wing_blast/end