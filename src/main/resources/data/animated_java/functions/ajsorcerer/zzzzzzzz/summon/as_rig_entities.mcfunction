scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #animated_java:ajsorcerer/on_summon/as_rig_entities
execute if entity @s[tag=aj.ajsorcerer.bone] run function #animated_java:ajsorcerer/zzzzzzzz/on_summon/as_bones

