#built using mc-build (https://github.com/mc-build/mc-build)

summon area_effect_cloud ~ ~ ~ {Tags:["playerdamager","justsummoned"],Owner:[I;0,0,0,0],Particle:"block air",Radius:1.0f,RadiusPerTick:0f,RadiusOnUse:0f,Duration:10,DurationOnUse:0f,WaitTime:0,Effects:[{Id:6,Amplifier:31b,Duration:10,ShowParticles:0b},{Id:7,Amplifier:31b,Duration:10,ShowParticles:0b}]}
data modify entity @e[tag=justsummoned,tag=playerdamager,limit=1] Owner set from entity @s UUID
tag @e[tag=justsummoned] remove justsummoned