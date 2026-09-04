scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.ajsorcererm2.export_version dummy
scoreboard objectives add aj.ajsorcererm2.rig_loaded dummy
scoreboard objectives add aj.ajsorcererm2.animation.fall.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.idle.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.risetransition.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.dashstrike.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.voidwave.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.javelinswing.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.airdash.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.voidbeam.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.seekingsingularities.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.die.local_anim_time dummy
scoreboard objectives add aj.ajsorcererm2.animation.fall.loop_mode dummy
scoreboard objectives add aj.ajsorcererm2.animation.idle.loop_mode dummy
scoreboard objectives add aj.ajsorcererm2.animation.risetransition.loop_mode dummy
scoreboard objectives add aj.ajsorcererm2.animation.dashstrike.loop_mode dummy
scoreboard objectives add aj.ajsorcererm2.animation.voidwave.loop_mode dummy
scoreboard objectives add aj.ajsorcererm2.animation.javelinswing.loop_mode dummy
scoreboard objectives add aj.ajsorcererm2.animation.airdash.loop_mode dummy
scoreboard objectives add aj.ajsorcererm2.animation.voidbeam.loop_mode dummy
scoreboard objectives add aj.ajsorcererm2.animation.seekingsingularities.loop_mode dummy
scoreboard objectives add aj.ajsorcererm2.animation.die.loop_mode dummy
scoreboard players set $aj.ajsorcererm2.animation.fall aj.id 0
scoreboard players set $aj.ajsorcererm2.animation.idle aj.id 1
scoreboard players set $aj.ajsorcererm2.animation.risetransition aj.id 2
scoreboard players set $aj.ajsorcererm2.animation.dashstrike aj.id 3
scoreboard players set $aj.ajsorcererm2.animation.voidwave aj.id 4
scoreboard players set $aj.ajsorcererm2.animation.javelinswing aj.id 5
scoreboard players set $aj.ajsorcererm2.animation.airdash aj.id 6
scoreboard players set $aj.ajsorcererm2.animation.voidbeam aj.id 7
scoreboard players set $aj.ajsorcererm2.animation.seekingsingularities aj.id 8
scoreboard players set $aj.ajsorcererm2.animation.die aj.id 9
scoreboard players set $aj.ajsorcererm2.variant.default aj.id 0
scoreboard players set $aj.ajsorcererm2.variant.slash aj.id 1
scoreboard players set $aj.ajsorcererm2.variant.invis aj.id 2
scoreboard players set $aj.ajsorcererm2.variant.staffless aj.id 3
scoreboard players set $aj.ajsorcererm2.variant.invisstaff aj.id 4
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set aj.ajsorcererm2.export_version aj.i -1166001892
scoreboard players reset * aj.ajsorcererm2.rig_loaded
execute as @e[type=minecraft:item_display,tag=aj.ajsorcererm2.root] run function animated_java:ajsorcererm2/zzzzzzzz/on_load