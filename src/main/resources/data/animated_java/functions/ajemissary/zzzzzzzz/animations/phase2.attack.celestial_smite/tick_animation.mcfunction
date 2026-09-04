scoreboard players add @s aj.ajemissary.animation.phase2.attack.celestial_smite.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajemissary.animation.phase2.attack.celestial_smite.local_anim_time
function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.celestial_smite/apply_frame_as_root
execute if score @s aj.ajemissary.animation.phase2.attack.celestial_smite.local_anim_time matches 110.. run function animated_java:ajemissary/zzzzzzzz/animations/phase2.attack.celestial_smite/end